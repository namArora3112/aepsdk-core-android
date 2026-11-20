/*
  Copyright 2026 Adobe. All rights reserved.
  This file is licensed to you under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0
*/

package com.adobe.marketing.mobile.internal.eventhub

import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.plugin.IEventTelemetryPlugin
import com.adobe.marketing.mobile.plugin.TelemetryApiCapability
import com.adobe.marketing.mobile.plugin.TelemetryHubEvent
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

/**
 * Minimal optional bridge owned by Core. It copies approved EventHub metadata and
 * hands it to the Telemetry SDK without applying capture policy, matching events,
 * timing API calls, or reading event data.
 */
internal object EventHubTelemetry {
    private val current = AtomicReference<Session?>()
    private val generations = AtomicLong()

    private class Session(
        val plugin: IEventTelemetryPlugin,
        val generation: Long
    ) {
        val dropped = AtomicLong()
        val worker = ThreadPoolExecutor(
            1, 1, 0, TimeUnit.MILLISECONDS, ArrayBlockingQueue(64),
            { task -> Thread(task, "aep-core-telemetry").apply { isDaemon = true } }
        )

        fun enqueue(action: () -> Unit): Boolean = try {
            worker.execute {
                if (current.get() !== this) return@execute
                try {
                    action()
                } catch (_: Exception) {
                    // Optional telemetry cannot affect business dispatch.
                } catch (_: LinkageError) {
                    // An incompatible optional implementation must not affect Core.
                }
            }
            true
        } catch (_: RejectedExecutionException) {
            dropped.incrementAndGet()
            false
        }

        fun close() = worker.shutdownNow()
    }

    @Synchronized
    fun configure(plugin: IEventTelemetryPlugin, enabled: Boolean): Long {
        val generation = generations.incrementAndGet()
        current.getAndSet(null)?.close()
        if (enabled) current.set(Session(plugin, generation))
        return generation
    }

    fun observe(event: Event) {
        val session = current.get() ?: return
        val fields = listOf(
            event.name, event.type, event.source,
            event.uniqueIdentifier, event.parentID, event.responseID
        )
        if (fields.any { it != null && it.length > 256 }) {
            session.dropped.incrementAndGet()
            return
        }
        val snapshot = TelemetryHubEvent(
            event.name, event.type, event.source, event.uniqueIdentifier,
            event.parentID, event.responseID, event.timestamp
        )
        session.enqueue { session.plugin.eventMapper(snapshot, session.generation) }
    }

    fun operationStarted(producer: String, eventName: String): String? =
        current.get()?.plugin?.operationStarted(producer, eventName)

    fun operationCompleted(
        operationId: String,
        producer: String,
        eventName: String,
        outcome: String,
        meaning: String
    ) = current.get()?.plugin?.operationCompleted(
        operationId, producer, eventName, outcome, meaning
    )

    fun declareApiCapabilities(producer: String, declarations: List<TelemetryApiCapability>): Boolean =
        current.get()?.plugin?.declareApiCapabilities(producer, declarations) == true

    fun apiStarted(producer: String, apiName: String): String? =
        current.get()?.plugin?.apiStarted(producer, apiName)

    fun apiCompleted(id: String, producer: String, apiName: String, outcome: String) =
        current.get()?.plugin?.apiCompleted(id, producer, apiName, outcome)

    fun setCaptureMode(mode: String): Boolean =
        current.get()?.plugin?.setCaptureMode(mode) == true

    fun migrationStatus(): Map<String, Any> =
        current.get()?.plugin?.migrationStatus() ?: mapOf(
            "droppedHandoffs" to 0L
        )

    fun expireApiOperations() = current.get()?.plugin?.expireApiOperations()

    @Synchronized
    fun shutdown() {
        current.getAndSet(null)?.close()
    }

    @Synchronized
    fun shutdownWithGeneration(): Long {
        shutdown()
        return generations.incrementAndGet()
    }
}
