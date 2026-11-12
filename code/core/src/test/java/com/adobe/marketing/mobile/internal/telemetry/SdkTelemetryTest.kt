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

package com.adobe.marketing.mobile.internal.telemetry

import com.adobe.marketing.mobile.internal.PluginRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SdkTelemetryTest {
    @Before
    fun setUp() {
        PluginRegistry.clear()
    }

    @After
    fun tearDown() {
        PluginRegistry.clear()
    }

    @Test
    fun recordsFixedMetadataOnlyWhenRegisteredAndPermitted() {
        recordCall()
        val plugin = register()
        recordCall()
        assertTrue(plugin.snapshot().isEmpty())

        plugin.updateCollectionPolicy(enabled = true, consentGranted = true)
        val before = System.currentTimeMillis()
        recordCall()

        val call = plugin.snapshot().single()
        assertEquals("com.adobe.messaging", call.extensionName)
        assertEquals("test-version", call.extensionVersion)
        assertEquals("Messaging.refreshInAppMessages", call.apiName)
        assertTrue(call.timestampMillis in before..System.currentTimeMillis())
    }

    @Test
    fun collectionRequiresBothEnablementAndGrantedConsent() {
        val plugin = register()
        for ((enabled, granted) in listOf(false to false, false to true, true to false)) {
            plugin.updateCollectionPolicy(enabled, granted)
            recordCall()
            assertTrue(plugin.snapshot().isEmpty())
        }
    }

    @Test
    fun disallowingCollectionPurgesBufferedAndDroppedRecords() {
        val plugin = register(capacity = 1)
        plugin.updateCollectionPolicy(enabled = true, consentGranted = true)
        recordCall()
        recordCall()
        assertEquals(1L, plugin.droppedCallCount())

        plugin.updateCollectionPolicy(enabled = true, consentGranted = false)
        recordCall()
        assertTrue(plugin.snapshot().isEmpty())
        assertEquals(0L, plugin.droppedCallCount())

        plugin.updateCollectionPolicy(enabled = true, consentGranted = true)
        recordCall()
        plugin.updateCollectionPolicy(enabled = false, consentGranted = true)
        assertTrue(plugin.snapshot().isEmpty())
    }

    @Test
    fun concurrentCallsCannotExceedCapacity() {
        val plugin = register(capacity = 10)
        plugin.updateCollectionPolicy(enabled = true, consentGranted = true)
        val executor = Executors.newFixedThreadPool(4)
        try {
            val futures = (1..8).map {
                executor.submit { repeat(32) { recordCall() } }
            }
            futures.forEach { it.get(5, TimeUnit.SECONDS) }
        } finally {
            executor.shutdownNow()
        }
        assertEquals(10, plugin.snapshot().size)
        assertEquals(246L, plugin.droppedCallCount())
    }

    @Test
    fun recorderFailureDoesNotEscapeTheBridge() {
        var attempted = false
        PluginRegistry.addPlugin(
            object : ApiCallRecorder {
                override fun recordApiCall(call: ApiCall) {
                    attempted = true
                    throw IllegalStateException("Recorder unavailable")
                }
            }
        )
        recordCall()
        assertTrue(attempted)
    }

    @Test
    fun registryStartsAndClosesTheSelectedWorkerLifecycle() {
        var starts = 0
        var invalidations = 0
        var closes = 0
        PluginRegistry.addPlugin(object : TelemetryLifecycle {
            override fun onRegistered() { starts++ }
            override fun invalidate() { invalidations++ }
            override fun close() { closes++ }
            override fun recordApiCall(call: ApiCall) {}
        })
        assertEquals(1, starts)
        SdkTelemetry.registrationStarted()
        SdkTelemetry.registrationCompleted()
        assertEquals(2, invalidations)
        PluginRegistry.clear()
        assertEquals(1, closes)
    }

    @Test
    fun shadowedWorkerDoesNotStartOutsideTheRecorderSelectionContract() {
        register()
        var started = false
        PluginRegistry.addPlugin(object : TelemetryLifecycle {
            override fun onRegistered() { started = true }
            override fun invalidate() {}
            override fun close() {}
            override fun recordApiCall(call: ApiCall) {}
        })
        assertTrue(!started)
    }

    private fun register(capacity: Int = 256): InMemoryTelemetryPlugin =
        InMemoryTelemetryPlugin(capacity).also { PluginRegistry.addPlugin(it) }

    private fun recordCall() {
        SdkTelemetry.recordApiCall("com.adobe.messaging", "test-version", "Messaging.refreshInAppMessages")
    }
}
