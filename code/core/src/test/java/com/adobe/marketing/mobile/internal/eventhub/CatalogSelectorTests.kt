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
import org.junit.After
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CatalogSelectorTests {
    private class Sink : ITelemetryPlugin {
        val events = CopyOnWriteArrayList<String>()
        val barrier = CountDownLatch(1)
        override fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long) {
            events.add(eventName)
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
            if (phase == "completed") barrier.countDown()
        }
        fun drain() {
            val id = assertNotNull(EventHubTelemetry.operationStarted("test", "barrier"))
            EventHubTelemetry.operationCompleted(id, "test", "barrier", "done", "")
            assertTrue(barrier.await(5, TimeUnit.SECONDS))
        }
    }

    @After
    fun reset() {
        EventHubTelemetry.shutdown()
        EventHubTelemetry.setCaptureMode("hybrid")
    }

    @Test
    fun onlyAnActualApprovedBooleanMatches() {
        val sink = Sink()
        EventHubTelemetry.setCaptureMode("hybrid")
        EventHubTelemetry.configure(
            sink, true,
            listOf(
                TelemetryEventMapping(
                    EventType.MESSAGING, EventSource.REQUEST_CONTENT, "sdk", "refresh",
                    requiredBooleanKey = "refreshmessages"
                )
            )
        )
        listOf(
            emptyMap(), mapOf("refreshmessages" to false), mapOf("refreshmessages" to "true"),
            mapOf("refreshmessages" to true)
        ).forEach {
            EventHubTelemetry.observe(Event.Builder("Refresh", EventType.MESSAGING, EventSource.REQUEST_CONTENT).setEventData(it).build())
        }
        sink.drain()
        assertEquals(listOf("refresh"), sink.events)
    }

    @Test
    fun ambiguousTrackingKeysMatchNeitherApiFamily() {
        val sink = Sink()
        EventHubTelemetry.setCaptureMode("hybrid")
        EventHubTelemetry.configure(
            sink, true,
            listOf(
                TelemetryEventMapping(EventType.GENERIC_TRACK, EventSource.REQUEST_CONTENT, "sdk", "action", requiredKey = "action", forbiddenKey = "state"),
                TelemetryEventMapping(EventType.GENERIC_TRACK, EventSource.REQUEST_CONTENT, "sdk", "state", requiredKey = "state", forbiddenKey = "action")
            )
        )
        listOf(
            mapOf("action" to "synthetic"), mapOf("state" to "synthetic"),
            mapOf("action" to "synthetic", "state" to "synthetic")
        ).forEach {
            EventHubTelemetry.observe(Event.Builder("Analytics Track", EventType.GENERIC_TRACK, EventSource.REQUEST_CONTENT).setEventData(it).build())
        }
        sink.drain()
        assertEquals(listOf("action", "state"), sink.events)
    }

    @Test
    fun unsupportedPayloadPredicatesDisableTheReplacementPolicy() {
        val sink = Sink()
        assertFailsWith<IllegalArgumentException> {
            EventHubTelemetry.configure(
                sink, true,
                listOf(
                    TelemetryEventMapping(
                        EventType.MESSAGING, EventSource.REQUEST_CONTENT, "sdk", "invalid",
                        requiredBooleanKey = "customerSecret"
                    )
                )
            )
        }
        assertEquals(0, sink.events.size)
    }
}
