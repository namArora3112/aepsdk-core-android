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
import com.adobe.marketing.mobile.telemetrybridge.TelemetryBridge
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HybridTelemetryTests {
    private open class OldSink : ITelemetryPlugin {
        val events = CopyOnWriteArrayList<String>()
        var barrier = CountDownLatch(1)
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
            if (eventName == "barrier" && phase == "completed") barrier.countDown()
        }
        fun drain() {
            barrier = CountDownLatch(1)
            val id = assertNotNull(TelemetryBridge.operationStarted("test", "barrier"))
            TelemetryBridge.operationCompleted(id, "test", "barrier", "done", "")
            assertTrue(barrier.await(5, TimeUnit.SECONDS))
        }
    }

    private class Sink : OldSink(), IApiTelemetryPlugin {
        val records = CopyOnWriteArrayList<TelemetryApiRecord>()
        val received = Semaphore(0)
        override fun recordApi(record: TelemetryApiRecord, generation: Long) {
            records.add(record)
            received.release()
        }
    }

    private fun capability(name: String, complete: Boolean = true) = TelemetryApiCapability(
        name, "1.0.0", outcomes = if (complete) setOf("success", "failure", "rejected", "timed_out", "cancelled") else emptySet(),
        outcomeContract = if (complete) "test.business.v1" else null
    )

    private fun mapping(name: String, apis: List<String> = listOf(name), legacy: Boolean = true) =
        TelemetryEventMapping(
            EventType.GENERIC_TRACK, EventSource.REQUEST_CONTENT, "sdk", name,
            retireWhenApisCovered = apis, legacyFallback = legacy
        )

    private fun observe() = EventHubTelemetry.observe(
        Event.Builder("test", EventType.GENERIC_TRACK, EventSource.REQUEST_CONTENT).build()
    )

    @Before
    fun setup() {
        EventHubTelemetry.shutdown()
        listOf("sdk", "sdk.two", "sdk.three").forEach { TelemetryBridge.declareApiCapabilities(it, emptyList()) }
        TelemetryBridge.setCaptureMode("hybrid")
        EventHubTelemetry.apiClock = System::nanoTime
    }

    @After
    fun teardown() = setup()

    @Test
    fun mixedOldEntryOnlyAndCompleteApisHaveDifferentCoverage() {
        val sink = Sink()
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("new"), capability("entry", false)))
        TelemetryBridge.configure(sink, true, listOf(mapping("old"), mapping("new"), mapping("entry")))
        observe()
        val named = assertNotNull(TelemetryBridge.apiStarted("sdk", "new"))
        TelemetryBridge.apiCompleted(named, "sdk", "new", "success")
        val entry = assertNotNull(TelemetryBridge.apiStarted("sdk", "entry"))
        TelemetryBridge.apiCompleted(entry, "sdk", "entry", "failure")
        assertNull(TelemetryBridge.apiStarted("sdk", "old"))
        sink.drain()
        assertEquals(listOf("old", "entry"), sink.events)
        assertEquals(2, sink.records.count { it.apiName == "new" })
        assertEquals("entry_only", sink.records.single { it.apiName == "entry" }.coverage)
        assertEquals("started", sink.records.single { it.apiName == "entry" }.phase)
    }

    @Test
    fun sharedSignatureRemainsUntilEveryDeclaredApiHasOutcomes() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, listOf(mapping("identity.observed", listOf("getIds", "getEcid"))))
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("getIds")))
        observe()
        sink.drain()
        assertEquals(1, sink.events.size)
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("getIds"), capability("getEcid", false)))
        observe()
        sink.drain()
        assertEquals(2, sink.events.size)
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("getIds"), capability("getEcid")))
        observe()
        sink.drain()
        assertEquals(2, sink.events.size)
    }

    @Test
    fun finalizedCoreConfigurationChangesFallbackButPinsActiveApiContract() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, listOf(mapping("old"), mapping("diagnostic", legacy = false)))
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("new")))
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "new"))
        EventHubTelemetry.observe(
            Event.Builder("configuration", EventType.CONFIGURATION, EventSource.RESPONSE_CONTENT)
                .setEventData(mapOf("telemetry.captureMode" to "direct_only")).build()
        )
        observe()
        TelemetryBridge.apiCompleted(id, "sdk", "new", "success")
        sink.drain()
        assertEquals(listOf("diagnostic"), sink.events)
        assertEquals(listOf("hybrid", "hybrid"), sink.records.map { it.captureMode })
        TelemetryBridge.setCaptureMode("hybrid")
        observe()
        sink.drain()
        assertEquals(listOf("diagnostic", "old", "diagnostic"), sink.events)
    }

    @Test
    fun updatedDeclarationsDoNotRewriteAnActiveCall() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api", false).copy(extensionVersion = "2.0.0")))
        TelemetryBridge.apiCompleted(id, "sdk", "api", "failure")
        sink.drain()
        assertTrue(sink.records.all { it.extensionVersion == "1.0.0" })
        assertEquals("failure", sink.records.last().outcome)
    }

    @Test
    fun mismatchedAndDuplicateCompletionsCannotStealOrDuplicateTheResult() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
        TelemetryBridge.apiCompleted(id, "wrong", "api", "failure")
        TelemetryBridge.apiCompleted(id, "sdk", "wrong", "failure")
        TelemetryBridge.apiCompleted(id, "sdk", "api", "not_a_contract_outcome")
        TelemetryBridge.operationCompleted(id, "sdk", "api", "success", "")
        TelemetryBridge.apiCompleted(id, "sdk", "api", "rejected")
        TelemetryBridge.apiCompleted(id, "sdk", "api", "failure")
        sink.drain()
        assertEquals(listOf(null, "rejected"), sink.records.map { it.outcome })
    }

    @Test
    fun missingCompletionExpiresAsUnknownNotAsTimeout() {
        val sink = Sink()
        var now = 0L
        EventHubTelemetry.apiClock = { now }
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api").copy(observationTtlMs = 10)))
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
        now = TimeUnit.MILLISECONDS.toNanos(11)
        TelemetryBridge.expireApiOperations()
        TelemetryBridge.apiCompleted(id, "sdk", "api", "timed_out")
        sink.drain()
        assertEquals("unknown", sink.records.last().outcome)
        assertEquals("completion_not_observed", sink.records.last().meaning)
        assertEquals(0, TelemetryBridge.migrationStatus()["pendingApis"])
    }

    @Test
    fun explicitFailureDeadlineAndCancellationStayDistinct() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        listOf("failure", "timed_out", "cancelled").forEach {
            val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
            TelemetryBridge.apiCompleted(id, "sdk", "api", it)
        }
        sink.drain()
        assertEquals(listOf("failure", "timed_out", "cancelled"), sink.records.filter { it.phase == "completed" }.map { it.outcome })
    }

    @Test
    fun entryOnlyCallsDoNotAccumulatePendingOperations() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api", false)))
        repeat(300) {
            assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
            assertTrue(sink.received.tryAcquire(5, TimeUnit.SECONDS))
        }
        assertEquals(0, TelemetryBridge.migrationStatus()["pendingApis"])
    }

    @Test
    fun pendingApiOperationsShareTheExistingHardLimit() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        val ids = (1..256).map {
            assertNotNull(TelemetryBridge.apiStarted("sdk", "api")).also {
                assertTrue(sink.received.tryAcquire(5, TimeUnit.SECONDS))
            }
        }
        assertNull(TelemetryBridge.apiStarted("sdk", "api"))
        assertNull(TelemetryBridge.operationStarted("sdk", "legacy"))
        TelemetryBridge.apiCompleted(ids.first(), "sdk", "api", "success")
        assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
    }

    @Test
    fun oldSinkAndAbsentSinkDoNotLoseFallbackOnCapabilityDeclaration() {
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        assertNull(TelemetryBridge.apiStarted("sdk", "api"))
        val sink = OldSink()
        TelemetryBridge.configure(sink, true, listOf(mapping("api")))
        assertNull(TelemetryBridge.apiStarted("sdk", "api"))
        observe()
        sink.drain()
        assertEquals(listOf("api"), sink.events)
    }

    @Test
    fun invalidDeclarationsRestoreFallbackAndDefensivelyCopyOutcomes() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, listOf(mapping("api")))
        val outcomes = mutableSetOf("success")
        assertTrue(TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api").copy(outcomes = outcomes))))
        outcomes.clear()
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
        TelemetryBridge.apiCompleted(id, "sdk", "api", "success")
        assertFalse(TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api").copy(outcomeContract = null))))
        observe()
        sink.drain()
        assertEquals("success", sink.records.last().outcome)
        assertEquals(listOf("api"), sink.events)
    }

    @Test
    fun registryIsBoundedAndDuplicateDeclarationsAreRejected() {
        val declarations = (1..64).map { capability("api$it") }
        assertTrue(TelemetryBridge.declareApiCapabilities("sdk", declarations))
        assertTrue(TelemetryBridge.declareApiCapabilities("sdk.two", declarations))
        assertFalse(TelemetryBridge.declareApiCapabilities("sdk.three", listOf(capability("extra"))))
        assertEquals(128, TelemetryBridge.migrationStatus()["declaredApis"])
        assertFalse(TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api"), capability("api"))))
        assertEquals(64, TelemetryBridge.migrationStatus()["declaredApis"])
    }

    @Test
    fun invalidModeFailsClosedForFallbackAndNeverDisablesNamedHooks() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, listOf(mapping("old")))
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("new")))
        assertFalse(TelemetryBridge.setCaptureMode("unexpected"))
        observe()
        val id = assertNotNull(TelemetryBridge.apiStarted("sdk", "new"))
        TelemetryBridge.apiCompleted(id, "sdk", "new", "success")
        sink.drain()
        assertTrue(sink.events.isEmpty())
        assertEquals(2, sink.records.size)
    }

    @Test
    fun permissionDisableDropsOldCallsAcrossReenable() {
        val sink = Sink()
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.declareApiCapabilities("sdk", listOf(capability("api")))
        val old = assertNotNull(TelemetryBridge.apiStarted("sdk", "api"))
        sink.drain()
        sink.records.clear()
        TelemetryBridge.configure(sink, false, emptyList())
        assertNull(TelemetryBridge.apiStarted("sdk", "api"))
        TelemetryBridge.configure(sink, true, emptyList())
        TelemetryBridge.apiCompleted(old, "sdk", "api", "success")
        sink.drain()
        assertTrue(sink.records.isEmpty())
    }
}
