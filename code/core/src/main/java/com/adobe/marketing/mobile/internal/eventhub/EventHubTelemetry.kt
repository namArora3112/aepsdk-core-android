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
import com.adobe.marketing.mobile.plugin.ITelemetryPlugin
import com.adobe.marketing.mobile.plugin.TelemetryEventMapping
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

    private data class Operation(val producer: String, val name: String, val startedNanos: Long)

    private class Session(
        val plugin: ITelemetryPlugin,
        val mappings: List<TelemetryEventMapping>,
        val generation: Long
    ) {
        val operations = mutableMapOf<String, Operation>()
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
        val catalog = mappings.toList()
        catalog.forEach {
            require(it.eventType.isNotBlank() && it.eventType != EventType.WILDCARD)
            require(it.eventSource.isNotBlank() && it.eventSource != EventSource.WILDCARD)
            require(it.producer.isNotBlank() && it.telemetryEventName.isNotBlank())
            require(it.requiredKey == null || it.requiredKey == "action" || it.requiredKey == "state")
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
        val operation = synchronized(session.operations) { session.operations.remove(id) } ?: return
        if (producer != operation.producer || name != operation.name) return
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
