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

import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.SharedStateResult
import com.adobe.marketing.mobile.SharedStateStatus
import com.adobe.marketing.mobile.internal.PluginRegistry
import com.adobe.marketing.mobile.internal.eventhub.EventHub
import com.adobe.marketing.mobile.internal.eventhub.EventHubConstants
import com.adobe.marketing.mobile.internal.eventhub.SharedStateType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.eq
import org.mockito.kotlin.isNull
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SdkTelemetrySnapshotTest {
    private lateinit var original: EventHub
    private val states = mutableMapOf<String, SharedStateResult>()
    private lateinit var hub: EventHub

    @Before
    fun setup() {
        PluginRegistry.clear()
        original = EventHub.shared
        hub = mock(EventHub::class.java)
        EventHub.shared = hub
        whenever(hub.getSharedState(any(), any(), isNull(), eq(false), any())).thenAnswer {
            val owner = it.getArgument<String>(1)
            if (owner == SdkTelemetry.CONSENT && it.getArgument<SharedStateType>(0) != SharedStateType.XDM) {
                null
            } else states[owner]
        }
        state(
            SdkTelemetry.CONFIGURATION,
            mapOf(
                SdkTelemetry.ENABLED to true,
                "global.privacy" to "optedin", "experienceCloud.org" to "TEST@AdobeOrg",
                "sdk.telemetry.edge.configId" to "configured-id",
                "sdk.telemetry.edge.endpoint" to "configured-endpoint"
            )
        )
        state(SdkTelemetry.CONSENT, mapOf("consents" to mapOf("collect" to mapOf("val" to "y"))))
        state(
            EventHubConstants.NAME,
            mapOf(
                "version" to "1",
                "extensions" to mapOf(
                    "com.adobe.messaging" to mapOf("version" to "2"),
                    "third.party.extension" to mapOf("version" to "3"),
                    SdkTelemetry.CONFIGURATION to mapOf("version" to "1"),
                    EventHubConstants.NAME to mapOf("version" to "1")
                )
            )
        )
    }

    private fun state(owner: String, data: Map<String, Any>) {
        states[owner] = SharedStateResult(SharedStateStatus.SET, data)
    }

    @After
    fun cleanup() {
        PluginRegistry.clear()
        EventHub.shared = original
    }

    @Test
    fun snapshotUsesXdmConsentAndPublicSdkInventoryWithoutInternalContributors() {
        SdkTelemetry.registrationStarted()
        assertFalse(SdkTelemetry.snapshot().permitted)
        SdkTelemetry.registrationCompleted()
        val snapshot = SdkTelemetry.snapshot()
        assertTrue(snapshot.permitted)
        assertEquals("configured-id", snapshot.telemetryConfigId)
        assertEquals("configured-endpoint", snapshot.telemetryEndpoint)
        assertEquals(
            mapOf(
                SdkTelemetry.CORE to "1", "com.adobe.messaging" to "2",
                "third.party.extension" to "3"
            ),
            snapshot.versions
        )
        verify(hub, atLeastOnce()).getSharedState(eq(SharedStateType.XDM), eq(SdkTelemetry.CONSENT), isNull(), eq(false), any())
        verify(hub, never()).getSharedState(eq(SharedStateType.STANDARD), eq(SdkTelemetry.CONSENT), isNull(), eq(false), any())
    }

    @Test
    fun missingOrPendingStatesBlockWithoutImplicitDenialWhileExplicitDisableDenies() {
        SdkTelemetry.registrationStarted()
        SdkTelemetry.registrationCompleted()
        states[SdkTelemetry.CONFIGURATION] = SharedStateResult(SharedStateStatus.PENDING, null)
        assertFalse(SdkTelemetry.snapshot().permitted)
        assertFalse(SdkTelemetry.snapshot().privacyDenied)
        state(SdkTelemetry.CONFIGURATION, mapOf(SdkTelemetry.ENABLED to false))
        assertTrue(SdkTelemetry.snapshot().privacyDenied)
        state(
            SdkTelemetry.CONFIGURATION,
            mapOf(
                SdkTelemetry.ENABLED to true, "global.privacy" to "optedin",
                "experienceCloud.org" to "TEST@AdobeOrg"
            )
        )
        states.remove(SdkTelemetry.CONSENT)
        assertFalse(SdkTelemetry.snapshot().permitted)
        assertFalse(SdkTelemetry.snapshot().privacyDenied)
    }

    @Test
    fun operationalHooksOnlyRecordFixedNamesNeverCallerArguments() {
        val recorder = InMemoryTelemetryPlugin()
        recorder.updateCollectionPolicy(true, true)
        PluginRegistry.addPlugin(recorder)
        MobileCore.trackAction("customer secret", mapOf("private" to "sensitive"))
        MobileCore.trackState("private screen", null)
        MobileCore.setPushIdentifier("private push token")
        MobileCore.resetIdentities()
        MobileCore.lifecycleStart(mapOf("secret" to "data"))
        MobileCore.lifecyclePause()
        assertEquals(
            listOf(
                "trackAction", "trackState", "setPushIdentifier", "resetIdentities",
                "lifecycleStart", "lifecyclePause"
            ),
            recorder.snapshot().map { it.apiName }
        )
        assertTrue(recorder.snapshot().all { it.extensionName == SdkTelemetry.CORE })
        assertFalse(recorder.snapshot().toString().contains("private"))
        assertFalse(recorder.snapshot().toString().contains("secret"))
    }

    @Test
    fun lateRegistrationCompletionFromResetGenerationCannotEnableNewInventory() {
        val old = SdkTelemetry.registrationStarted()
        SdkTelemetry.reset()
        val current = SdkTelemetry.registrationStarted()
        SdkTelemetry.registrationCompleted(old)
        assertFalse(SdkTelemetry.snapshot().registrationComplete)
        SdkTelemetry.registrationCompleted(current)
        assertTrue(SdkTelemetry.snapshot().registrationComplete)
    }
}
