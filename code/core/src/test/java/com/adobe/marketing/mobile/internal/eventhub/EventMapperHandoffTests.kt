package com.adobe.marketing.mobile.internal.eventhub

import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.EventSource
import com.adobe.marketing.mobile.EventType
import com.adobe.marketing.mobile.plugin.IEventTelemetryPlugin
import com.adobe.marketing.mobile.plugin.TelemetryHubEvent
import com.adobe.marketing.mobile.plugin.ITelemetryPlugin
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class EventMapperHandoffTests {
    private class Sink(
        private val count: Int = 1,
        private val latch: CountDownLatch = CountDownLatch(count)
    ) : IEventTelemetryPlugin {
        val events = CopyOnWriteArrayList<Pair<TelemetryHubEvent, Long>>()
        override fun eventMapper(event: TelemetryHubEvent, generation: Long) {
            events += event to generation
            latch.countDown()
        }
        override fun recordEvent(
            producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long
        ) {}
        override fun recordOperation(
            operationId: String, producer: String, eventName: String, phase: String,
            outcome: String?, meaning: String?, durationMs: Long?, generation: Long
        ) {}
        fun await() = latch.await(2, TimeUnit.SECONDS)
    }

    @Before @After fun reset() = EventHubTelemetry.shutdown()

    @Test fun forwardsOnlyApprovedMetadata() {
        val sink = Sink()
        val generation = EventHubTelemetry.configure(sink, true)
        val original = Event.Builder("Refresh", EventType.MESSAGING, EventSource.REQUEST_CONTENT)
            .setEventData(mapOf("secret" to "must-not-cross", "state" to true))
            .build()

        EventHubTelemetry.observe(original)
        assertTrue(sink.await())
        val delivered = sink.events.single()
        assertEquals(generation, delivered.second)
        assertEquals(original.name, delivered.first.name)
        assertEquals(original.type, delivered.first.type)
        assertEquals(original.source, delivered.first.source)
        assertEquals(original.uniqueIdentifier, delivered.first.eventId)
        assertNull(delivered.first.javaClass.declaredFields.firstOrNull {
            it.name == "eventData"
        })
    }

    @Test fun disabledObserverDoesNotDeliver() {
        val sink = Sink()
        EventHubTelemetry.configure(sink, false)
        EventHubTelemetry.observe(
            Event.Builder("Refresh", EventType.MESSAGING, EventSource.REQUEST_CONTENT).build()
        )
        assertTrue(sink.events.isEmpty())
    }

    @Test fun directHooksDelegateToTelemetryOwnedImplementation() {
        val sink = object : IEventTelemetryPlugin by Sink() {
            override fun operationStarted(producer: String, eventName: String) =
                "telemetry-owned-id"
        }
        EventHubTelemetry.configure(sink, true)
        assertEquals(
            "telemetry-owned-id",
            EventHubTelemetry.operationStarted("com.adobe.module.messaging", "refreshInAppMessages")
        )
    }
}
