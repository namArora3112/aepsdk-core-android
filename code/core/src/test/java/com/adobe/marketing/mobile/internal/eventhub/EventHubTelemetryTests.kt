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
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.plugin.ITelemetryPlugin
import com.adobe.marketing.mobile.plugin.TelemetryEventMapping
import com.adobe.marketing.mobile.telemetrybridge.TelemetryBridge
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EventHubTelemetryTests {
    private data class Record(val name: String, val id: String, val timestamp: Long)

    private open class Sink(expected: Int = 0) : ITelemetryPlugin {
        val records = CopyOnWriteArrayList<Record>()
        val operations = CopyOnWriteArrayList<String>()
        val received = CountDownLatch(expected)
        val barrier = CountDownLatch(1)

        override fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long) {
            records.add(Record(eventName, eventId, timestamp))
            received.countDown()
        }

        override fun recordOperation(
            operationId: String,
            producer: String,
            eventName: String,
            phase: String,
            outcome: String?,
            meaning: String?,
            durationMs: Long?,
            generation: Long
        ) {
            operations.add("$eventName:$phase:$operationId")
            if (eventName == "barrier") barrier.countDown()
        }

        fun drain() {
            assertNotNull(TelemetryBridge.operationStarted("test", "barrier"))
            assertTrue(barrier.await(5, TimeUnit.SECONDS))
        }
    }

    private fun mapping(
        type: String = EventType.GENERIC_TRACK,
        source: String = EventSource.REQUEST_CONTENT,
        name: String = "sdk.core.track",
        hubName: String? = null,
        xdm: String? = null,
        key: String? = null,
        enabled: Boolean = true
    ) = TelemetryEventMapping(type, source, "reviewed.producer", name, hubName, xdm, key, enabled)

    private fun event(
        type: String = EventType.GENERIC_TRACK,
        source: String = EventSource.REQUEST_CONTENT,
        name: String = "SDK request",
        data: Map<String, Any>? = null
    ): Event = Event.Builder(name, type, source).setEventData(data).build()

    @Before
    fun setup() {
        EventHubTelemetry.shutdown()
    }

    @After
    fun teardown() {
        EventHubTelemetry.shutdown()
    }

    @Test
    fun absentPluginIsANoop() {
        EventHubTelemetry.observe(event())
        assertNull(TelemetryBridge.operationStarted("sdk", "local.work"))
        TelemetryBridge.operationCompleted(null, "sdk", "local.work", "ignored", "absent")
    }

    @Test
    fun actualCoreApisAreCapturedOnceWithoutCustomerValues() {
        val hub = EventHub()
        val oldHub = EventHub.shared
        EventHub.shared = hub
        val sink = Sink(3)
        TelemetryBridge.configure(
            sink, true,
            listOf(
                mapping(name = "sdk.core.trackAction", key = "action"),
                mapping(name = "sdk.core.trackState", key = "state"),
                mapping(type = EventType.GENERIC_LIFECYCLE, name = "sdk.lifecycle.resume")
            )
        )
        try {
            MobileCore.trackAction("private-action", mapOf("secret" to "not-telemetry"))
            MobileCore.trackState("private-screen", null)
            MobileCore.lifecycleStart(mapOf("secret" to "not-telemetry"))
            assertTrue(sink.received.await(5, TimeUnit.SECONDS))
            assertEquals(
                listOf("sdk.core.trackAction", "sdk.core.trackState", "sdk.lifecycle.resume"),
                sink.records.map { it.name }
            )
            assertTrue(sink.operations.isEmpty())
            assertEquals(3, sink.records.map { it.id }.distinct().size)
        } finally {
            hub.shutdown()
            EventHub.shared = oldHub
        }
    }

    @Test
    fun catalogUsesTypeSourceNameAndSeparateXdmSelector() {
        val sink = Sink(2)
        TelemetryBridge.configure(
            sink, true,
            listOf(
                mapping(type = EventType.EDGE, name = "media.play", xdm = "media.*"),
                mapping(type = EventType.CAMPAIGN, name = "campaign.registerDevice", hubName = "Register")
            )
        )
        EventHubTelemetry.observe(event(EventType.EDGE, data = mapOf("xdm" to mapOf("eventType" to "media.play", "secret" to "hidden"))))
        EventHubTelemetry.observe(event(EventType.EDGE, data = mapOf("xdm" to mapOf("eventType" to "commerce.purchase"))))
        EventHubTelemetry.observe(event(EventType.EDGE, EventSource.RESPONSE_CONTENT))
        EventHubTelemetry.observe(event(EventType.CAMPAIGN, name = "Register"))
        EventHubTelemetry.observe(event(EventType.CAMPAIGN, name = "Other"))
        sink.drain()
        assertEquals(listOf("media.play", "campaign.registerDevice"), sink.records.map { it.name })
    }

    @Test
    fun duplicateEnabledMappingsDoNotCollapseDistinctCalls() {
        val sink = Sink()
        val first = event()
        val second = event()
        TelemetryBridge.configure(sink, true, listOf(mapping(enabled = false), mapping(), mapping()))
        EventHubTelemetry.observe(first)
        EventHubTelemetry.observe(second)
        sink.drain()
        assertEquals(listOf(first.uniqueIdentifier, second.uniqueIdentifier), sink.records.map { it.id })
        assertEquals(first.timestamp, sink.records.first().timestamp)
    }

    @Test
    fun disabledCaptureIsNotBackfilledOnEnable() {
        val sink = Sink()
        TelemetryBridge.configure(sink, false, listOf(mapping()))
        EventHubTelemetry.observe(event())
        assertNull(TelemetryBridge.operationStarted("sdk", "local.work"))
        TelemetryBridge.configure(sink, true, listOf(mapping()))
        sink.drain()
        assertTrue(sink.records.isEmpty())
    }

    @Test
    fun policyReplacementDropsQueuedWorkEvenAfterReenable() {
        val entered = CountDownLatch(1)
        val release = CountDownLatch(1)
        val old = object : Sink() {
            override fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long) {
                entered.countDown()
                try {
                    release.await(5, TimeUnit.SECONDS)
                } catch (_: InterruptedException) {
                    return
                }
                super.recordEvent(producer, eventName, eventId, timestamp, generation)
            }
        }
        TelemetryBridge.configure(old, true, listOf(mapping()))
        EventHubTelemetry.observe(event())
        assertTrue(entered.await(5, TimeUnit.SECONDS))
        EventHubTelemetry.observe(event())
        TelemetryBridge.configure(old, false, emptyList())
        val next = Sink()
        TelemetryBridge.configure(next, true, listOf(mapping()))
        release.countDown()
        next.drain()
        assertTrue(next.records.isEmpty())
        assertTrue(old.records.isEmpty())
    }

    @Test
    fun throwingCallbackDoesNotSuppressOtherMappingsOrLaterEvents() {
        val sink = object : Sink() {
            override fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long) {
                if (eventName == "bad") throw IllegalStateException("test failure")
                super.recordEvent(producer, eventName, eventId, timestamp, generation)
            }
        }
        TelemetryBridge.configure(sink, true, listOf(mapping(name = "bad"), mapping(name = "good")))
        repeat(2) { EventHubTelemetry.observe(event()) }
        sink.drain()
        assertEquals(listOf("good", "good"), sink.records.map { it.name })
    }

    @Test
    fun queueIsBoundedWhilePluginIsBlocked() {
        val entered = CountDownLatch(1)
        val release = CountDownLatch(1)
        val sink = object : Sink(65) {
            override fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long) {
                entered.countDown()
                release.await(5, TimeUnit.SECONDS)
                super.recordEvent(producer, eventName, eventId, timestamp, generation)
            }
        }
        TelemetryBridge.configure(sink, true, listOf(mapping()))
        EventHubTelemetry.observe(event())
        assertTrue(entered.await(5, TimeUnit.SECONDS))
        repeat(100) { EventHubTelemetry.observe(event()) }
        assertNull(TelemetryBridge.operationStarted("sdk", "overflow"))
        release.countDown()
        assertTrue(sink.received.await(5, TimeUnit.SECONDS))
        sink.drain()
        assertEquals(65, sink.records.size)
    }

    @Test
    fun typedDirectOperationsHavePairedIdsAndDoNotSurviveDisable() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        val id = assertNotNull(TelemetryBridge.operationStarted("sdk", "local.work"))
        TelemetryBridge.operationCompleted(id, "sdk", "local.work", "rejected", "invalid input")
        sink.drain()
        assertEquals(listOf("local.work:started:$id", "local.work:completed:$id"), sink.operations.take(2))
        TelemetryBridge.configure(sink, false, emptyList())
        assertNull(TelemetryBridge.operationStarted("sdk", "local.work"))
    }

    @Test
    fun wildcardHubAndArbitraryDataSelectorsAreRejected() {
        val sink = Sink()
        assertFailsWith<IllegalArgumentException> {
            TelemetryBridge.configure(sink, true, listOf(mapping(type = EventType.WILDCARD)))
        }
        assertFailsWith<IllegalArgumentException> {
            TelemetryBridge.configure(sink, true, listOf(mapping(key = "customerId")))
        }
        assertFailsWith<IllegalArgumentException> {
            TelemetryBridge.configure(sink, true, listOf(mapping(xdm = "*customer*")))
        }
    }

    @Test
    fun denialTakesEffectEvenWhenCatalogIsInvalid() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, listOf(mapping()))
        TelemetryBridge.configure(sink, false, listOf(mapping(type = EventType.WILDCARD)))
        assertNull(TelemetryBridge.operationStarted("sdk", "local.work"))
    }

    @Test
    fun unfinishedDirectOperationsAreBoundedAndCompletionReleasesCapacity() {
        val delivered = Semaphore(0)
        val sink = object : Sink() {
            override fun recordOperation(
                operationId: String,
                producer: String,
                eventName: String,
                phase: String,
                outcome: String?,
                meaning: String?,
                durationMs: Long?,
                generation: Long
            ) {
                delivered.release()
            }
        }
        TelemetryBridge.configure(sink, true, emptyList())
        val ids = (1..256).map {
            val id = assertNotNull(TelemetryBridge.operationStarted("sdk", "local.work"))
            assertTrue(delivered.tryAcquire(5, TimeUnit.SECONDS))
            id
        }
        assertNull(TelemetryBridge.operationStarted("sdk", "local.work"))
        TelemetryBridge.operationCompleted(ids.first(), "sdk", "local.work", "done", "")
        assertTrue(delivered.tryAcquire(5, TimeUnit.SECONDS))
        assertNotNull(TelemetryBridge.operationStarted("sdk", "local.work"))
    }
}
