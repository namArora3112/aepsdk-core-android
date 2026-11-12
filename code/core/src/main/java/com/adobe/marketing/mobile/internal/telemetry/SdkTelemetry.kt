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

import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.SharedStateResolution
import com.adobe.marketing.mobile.SharedStateStatus
import com.adobe.marketing.mobile.internal.PluginRegistry
import com.adobe.marketing.mobile.internal.eventhub.EventHub
import com.adobe.marketing.mobile.internal.eventhub.EventHubConstants
import com.adobe.marketing.mobile.internal.eventhub.SharedStateType
import com.adobe.marketing.mobile.plugin.IAepPlugin
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

data class ApiCall(
    val extensionName: String,
    val extensionVersion: String,
    val apiName: String,
    val timestampMillis: Long
)

/** SDK-only contract. Implementations must not perform disk or network work on this callback. */
interface ApiCallRecorder : IAepPlugin {
    fun recordApiCall(call: ApiCall)
}

/** Optional worker lifecycle; none of these callbacks may wait for disk or the EventHub. */
interface TelemetryLifecycle : ApiCallRecorder {
    fun onRegistered()
    fun invalidate()
    fun close()
}

data class TelemetrySnapshot(
    val enabled: Boolean,
    val orgId: String?,
    val privacy: String?,
    val collect: String?,
    val versions: Map<String, String>,
    val telemetryConfigId: String? = null,
    val telemetryEndpoint: String? = null,
    val configurationReady: Boolean = true,
    val registrationComplete: Boolean = true
) {
    val privacyDenied: Boolean
        get() = privacy == "optedout" || collect == "n" || (configurationReady && !enabled)
    val permitted: Boolean
        get() = enabled && configurationReady && registrationComplete &&
            privacy == "optedin" && collect == "y" &&
            orgId?.matches(Regex("[A-Za-z0-9]{1,64}@AdobeOrg")) == true && versions.isNotEmpty()
}

object SdkTelemetry {
    const val EVENT_TYPE = "com.adobe.eventType.sdkTelemetry"
    const val ENABLED = "sdk.telemetry.enabled"
    const val CORE = "com.adobe.module.core"
    const val CONFIGURATION = "com.adobe.module.configuration"
    const val CONSENT = "com.adobe.edge.consent"
    private val registrations = AtomicInteger()
    private val registrationGeneration = AtomicLong()
    private val registrationLock = Any()
    @Volatile private var completedRegistration = false

    @JvmStatic
    fun startRegisteredPlugin() {
        safely { lifecycle()?.onRegistered() }
    }

    @JvmStatic
    fun registrationStarted(): Long {
        val generation = synchronized(registrationLock) {
            registrations.incrementAndGet()
            registrationGeneration.get()
        }
        safely { lifecycle()?.invalidate() }
        return generation
    }

    @JvmStatic
    @JvmOverloads
    fun registrationCompleted(generation: Long = registrationGeneration.get()) {
        synchronized(registrationLock) {
            if (generation != registrationGeneration.get()) return
            completedRegistration = true
            registrations.decrementAndGet()
        }
        safely { lifecycle()?.invalidate() }
    }

    @JvmStatic
    fun reset() {
        synchronized(registrationLock) {
            registrationGeneration.incrementAndGet()
            completedRegistration = false
            registrations.set(0)
        }
    }

    /** Shared-state reads may wait for the hub. Worker threads only, never the API facade. */
    @JvmStatic
    @JvmOverloads
    fun snapshot(event: Event? = null): TelemetrySnapshot {
        fun state(owner: String, type: SharedStateType = SharedStateType.STANDARD): Map<String, Any?>? {
            val result = EventHub.shared.getSharedState(
                type, owner, event, false, SharedStateResolution.ANY
            )
            return if (result?.status == SharedStateStatus.SET) result.value else null
        }
        val configuration = state(CONFIGURATION)
        val hub = state(EventHubConstants.NAME)
        val versions = sortedMapOf<String, String>()
        (hub?.get("version") as? String)?.let { versions[CORE] = it }
        (hub?.get("extensions") as? Map<*, *>)?.forEach { (name, details) ->
            val version = (details as? Map<*, *>)?.get("version") as? String
            if (name is String && name != CONFIGURATION && name != EventHubConstants.NAME &&
                !version.isNullOrBlank()
            ) versions[name] = version
        }
        val consents = state(CONSENT, SharedStateType.XDM)?.get("consents") as? Map<*, *>
        return TelemetrySnapshot(
            configuration?.get(ENABLED) == true,
            configuration?.get("experienceCloud.org") as? String,
            configuration?.get("global.privacy") as? String,
            (consents?.get("collect") as? Map<*, *>)?.get("val") as? String,
            versions.toMap(),
            configuration?.get("sdk.telemetry.edge.configId") as? String,
            configuration?.get("sdk.telemetry.edge.endpoint") as? String,
            configuration != null,
            synchronized(registrationLock) { completedRegistration && registrations.get() == 0 }
        )
    }

    @JvmStatic
    fun recordApiCall(extensionName: String, extensionVersion: String, apiName: String) {
        safely {
            val recorder = PluginRegistry.getPlugin(ApiCallRecorder::class.java) ?: return
            recorder.recordApiCall(
                ApiCall(extensionName, extensionVersion, apiName, System.currentTimeMillis())
            )
        }
    }

    private inline fun safely(block: () -> Unit) {
        try {
            block()
        } catch (_: RuntimeException) {
            // Optional telemetry must not interrupt the SDK operation being observed.
        }
    }

    private fun lifecycle(): TelemetryLifecycle? =
        PluginRegistry.getPlugin(ApiCallRecorder::class.java) as? TelemetryLifecycle
}
