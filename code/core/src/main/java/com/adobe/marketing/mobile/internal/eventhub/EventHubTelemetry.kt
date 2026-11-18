/*
  Copyright 2026 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software distributed under
  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  OF ANY KIND, either express or implied. See the License for the specific language
  governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.internal.eventhub

import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.EventSource
import com.adobe.marketing.mobile.EventType
import com.adobe.marketing.mobile.plugin.IApiTelemetryPlugin
import com.adobe.marketing.mobile.plugin.ITelemetryPlugin
import com.adobe.marketing.mobile.plugin.TelemetryApiCapability
import com.adobe.marketing.mobile.plugin.TelemetryApiRecord
import com.adobe.marketing.mobile.plugin.TelemetryEventMapping
import java.util.Collections
import java.util.UUID
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

internal object EventHubTelemetry {
    private val current = AtomicReference<Session?>()
    private val generations = AtomicLong()
    private val captureMode = AtomicReference("hybrid")
    private val capabilities = AtomicReference<Map<Pair<String, String>, TelemetryApiCapability>>(emptyMap())
    internal var apiClock: () -> Long = System::nanoTime

    private data class Operation(
        val producer: String,
        val name: String,
        val startedNanos: Long,
        val capability: TelemetryApiCapability? = null,
        val mode: String = "hybrid"
    )

    private class Session(
        val plugin: ITelemetryPlugin,
        val mappings: List<TelemetryEventMapping>,
        val generation: Long
    ) {
        val operations = mutableMapOf<String, Operation>()
        val dropped = AtomicLong()
        val unknown = AtomicLong()
        val suppressed = AtomicLong()
        val worker = ThreadPoolExecutor(
            1, 1, 0, TimeUnit.MILLISECONDS, ArrayBlockingQueue(64),
            { task -> Thread(task, "aep-core-telemetry").apply { isDaemon = true } }
        )

        fun enqueue(action: () -> Unit): Boolean {
            return try {
                worker.execute {
                    if (current.get() !== this) return@execute
                    try {
                        action()
                    } catch (_: Exception) {
                        // Telemetry callbacks cannot affect customer dispatch.
                    } catch (_: LinkageError) {
                        // An incompatible optional plugin must not break Core.
                    }
                }
                true
            } catch (_: RejectedExecutionException) {
                dropped.incrementAndGet()
                false
            }
        }

        fun close() {
            worker.shutdownNow()
            synchronized(operations) { operations.clear() }
        }
    }

    @Synchronized
    fun configure(plugin: ITelemetryPlugin, enabled: Boolean, mappings: List<TelemetryEventMapping>): Long {
        val generation = generations.incrementAndGet()
        if (!enabled) {
            shutdown()
            return generation
        }
        // A malformed replacement policy must fail closed, not leave the previous policy active.
        current.getAndSet(null)?.close()
        require(mappings.size <= 64) { "Telemetry catalog exceeds 64 selectors" }
        val catalog = mappings.map { it.copy(retireWhenApisCovered = it.retireWhenApisCovered.toList()) }
        catalog.forEach {
            require(it.eventType.isNotBlank() && it.eventType != EventType.WILDCARD)
            require(it.eventSource.isNotBlank() && it.eventSource != EventSource.WILDCARD)
            require(it.producer.isNotBlank() && it.telemetryEventName.isNotBlank())
            require(it.requiredKey == null || it.requiredKey == "action" || it.requiredKey == "state")
            require(it.retireWhenApisCovered.size <= 16 && it.retireWhenApisCovered.all(::validName))
            val xdmType = it.xdmEventType
            require(
                xdmType == null || xdmType.isNotBlank() &&
                    (!xdmType.contains('*') || xdmType.endsWith(".*") && xdmType.count { c -> c == '*' } == 1)
            )
        }
        current.set(Session(plugin, catalog, generation))
        return generation
    }

    fun observe(event: Event) {
        if (event.type.equals(EventType.CONFIGURATION, true) &&
            event.source.equals(EventSource.RESPONSE_CONTENT, true) &&
            event.eventData?.containsKey("telemetry.captureMode") == true
        ) {
            setCaptureMode(event.eventData?.get("telemetry.captureMode") as? String ?: "")
        }
        val session = current.get() ?: return
        val matched = session.mappings.filter { mapping ->
            mapping.enabled &&
                mapping.eventType.equals(event.type, ignoreCase = true) &&
                mapping.eventSource.equals(event.source, ignoreCase = true) &&
                (mapping.eventName == null || mapping.eventName == event.name) &&
                (mapping.requiredKey == null || event.eventData?.containsKey(mapping.requiredKey) == true) &&
                matchesXdm(mapping.xdmEventType, event)
        }.distinctBy { it.producer to it.telemetryEventName }
        if (matched.isEmpty()) return
        val id = event.uniqueIdentifier
        val timestamp = event.timestamp
        // The task retains only catalog constants and correlation metadata, never Event/payload.
        session.enqueue {
            matched.forEach { mapping ->
                if (!mappingActive(session, mapping)) {
                    session.suppressed.incrementAndGet()
                    return@forEach
                }
                if (current.get() === session) {
                    try {
                        session.plugin.recordEvent(mapping.producer, mapping.telemetryEventName, id, timestamp, session.generation)
                    } catch (_: Exception) {
                        // One failing mapping must not suppress the remaining mappings.
                    } catch (_: LinkageError) {
                        // Optional plugin version mismatch.
                    }
                }
            }
        }
    }

    private fun matchesXdm(selector: String?, event: Event): Boolean {
        if (selector == null) return true
        val type = (event.eventData?.get("xdm") as? Map<*, *>)?.get("eventType") as? String ?: return false
        return if (selector.endsWith(".*")) type.startsWith(selector.dropLast(1)) else type == selector
    }

    private fun validName(value: String): Boolean =
        value.length in 1..160 && value.matches(Regex("[A-Za-z0-9_.-]+"))

    @Synchronized
    fun declareApiCapabilities(producer: String, declarations: List<TelemetryApiCapability>): Boolean {
        if (!validName(producer)) return false
        val remaining = capabilities.get().filterKeys { it.first != producer }
        val valid = declarations.size <= 64 &&
            remaining.size + declarations.size <= 128 &&
            declarations.map { it.apiName }.distinct().size == declarations.size &&
            declarations.all {
                validName(it.apiName) && validName(it.extensionVersion) && it.contractVersion > 0 &&
                    it.observationTtlMs in 1..3_600_000 &&
                    (it.outcomes.isEmpty() || it.outcomeContract?.let(::validName) == true) &&
                    it.outcomes.all { outcome ->
                        outcome in setOf("success", "failure", "rejected", "timed_out", "cancelled", "ignored")
                    }
            }
        capabilities.set(
            if (!valid) remaining else remaining + declarations.associate {
                (producer to it.apiName) to it.copy(outcomes = Collections.unmodifiableSet(it.outcomes.toSet()))
            }
        )
        return valid
    }

    fun setCaptureMode(mode: String): Boolean {
        val valid = mode == "hybrid" || mode == "direct_only"
        captureMode.set(if (valid) mode else "direct_only")
        return valid
    }

    private fun mappingActive(session: Session, mapping: TelemetryEventMapping): Boolean {
        if (!mapping.legacyFallback) return true
        if (captureMode.get() == "direct_only") return false
        if (session.plugin !is IApiTelemetryPlugin || mapping.retireWhenApisCovered.isEmpty()) return true
        val declared = capabilities.get()
        return !mapping.retireWhenApisCovered.all {
            declared[mapping.producer to it]?.outcomes?.isNotEmpty() == true
        }
    }

    fun apiStarted(producer: String, name: String): String? {
        expireApiOperations()
        val session = current.get() ?: return null
        if (session.plugin !is IApiTelemetryPlugin) return null
        val capability = capabilities.get()[producer to name] ?: return null
        synchronized(session.operations) {
            if (session.operations.size >= 256 || current.get() !== session) return null
            val id = UUID.randomUUID().toString()
            val operation = Operation(producer, name, apiClock(), capability, captureMode.get())
            if (capability.outcomes.isNotEmpty()) session.operations[id] = operation
            if (!session.enqueue { session.plugin.recordApi(apiRecord(id, operation, "started"), session.generation) }) {
                session.operations.remove(id)
                return null
            }
            return id
        }
    }

    private fun apiRecord(
        id: String,
        operation: Operation,
        phase: String,
        outcome: String? = null,
        meaning: String? = null
    ): TelemetryApiRecord {
        val capability = requireNotNull(operation.capability)
        return TelemetryApiRecord(
            id, operation.producer, operation.name, phase, capability.extensionVersion,
            capability.contractVersion, capability.outcomeContract,
            if (capability.outcomes.isEmpty()) "entry_only" else "entry_and_outcome",
            operation.mode, outcome, meaning,
            if (phase == "started") null else TimeUnit.NANOSECONDS.toMillis((apiClock() - operation.startedNanos).coerceAtLeast(0))
        )
    }

    fun apiCompleted(id: String, producer: String, name: String, outcome: String) {
        val session = current.get() ?: return
        val plugin = session.plugin as? IApiTelemetryPlugin ?: return
        synchronized(session.operations) {
            val operation = session.operations[id] ?: return
            if (operation.producer != producer || operation.name != name ||
                operation.capability?.outcomes?.contains(outcome) != true
            ) return
            session.operations.remove(id)
            val record = apiRecord(id, operation, "completed", outcome, operation.capability.outcomeContract)
            session.enqueue { plugin.recordApi(record, session.generation) }
        }
    }

    fun expireApiOperations() {
        val session = current.get() ?: return
        val plugin = session.plugin as? IApiTelemetryPlugin ?: return
        synchronized(session.operations) {
            val expired = session.operations.filterValues {
                val capability = it.capability
                capability != null && apiClock() - it.startedNanos >= TimeUnit.MILLISECONDS.toNanos(capability.observationTtlMs)
            }
            expired.forEach { (id, operation) ->
                session.operations.remove(id)
                session.unknown.incrementAndGet()
                val record = apiRecord(id, operation, "completed", "unknown", "completion_not_observed")
                session.enqueue { plugin.recordApi(record, session.generation) }
            }
        }
    }

    fun migrationStatus(): Map<String, Any> {
        val session = current.get()
        val declared = capabilities.get().values
        return mapOf(
            "captureMode" to captureMode.get(),
            "declaredApis" to declared.size,
            "outcomeApis" to declared.count { it.outcomes.isNotEmpty() },
            "pendingApis" to (session?.let { synchronized(it.operations) { it.operations.values.count { op -> op.capability != null } } } ?: 0),
            "droppedHandoffs" to (session?.dropped?.get() ?: 0L),
            "unknownOutcomes" to (session?.unknown?.get() ?: 0L),
            "suppressedFallbacks" to (session?.suppressed?.get() ?: 0L)
        )
    }

    fun operationStarted(producer: String, eventName: String): String? {
        val session = current.get() ?: return null
        if (producer.isBlank() || eventName.isBlank()) return null
        synchronized(session.operations) {
            if (session.operations.size >= 256 || current.get() !== session) return null
            val id = UUID.randomUUID().toString()
            session.operations[id] = Operation(producer, eventName, System.nanoTime())
            if (!session.enqueue {
                session.plugin.recordOperation(id, producer, eventName, "started", null, null, null, session.generation)
            }
            ) {
                session.operations.remove(id)
                return null
            }
            return id
        }
    }

    fun operationCompleted(id: String, producer: String, name: String, outcome: String, meaning: String) {
        val session = current.get() ?: return
        val operation = synchronized(session.operations) {
            val pending = session.operations[id] ?: return
            if (producer != pending.producer || name != pending.name || pending.capability != null) return
            session.operations.remove(id)
        } ?: return
        val duration = TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - operation.startedNanos).coerceAtLeast(0))
        session.enqueue {
            session.plugin.recordOperation(id, producer, name, "completed", outcome, meaning, duration, session.generation)
        }
    }

    @Synchronized
    fun shutdown() {
        current.getAndSet(null)?.close()
    }
}
