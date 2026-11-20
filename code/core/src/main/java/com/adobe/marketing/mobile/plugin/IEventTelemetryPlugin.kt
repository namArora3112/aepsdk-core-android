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

package com.adobe.marketing.mobile.plugin

/** Optional metadata mapper, invoked on Core's bounded telemetry worker only after explicit opt-in. */
interface IEventTelemetryPlugin : ITelemetryPlugin {
    fun eventMapper(event: TelemetryHubEvent, generation: Long)

    fun operationStarted(producer: String, eventName: String): String? = null

    fun operationCompleted(
        operationId: String,
        producer: String,
        eventName: String,
        outcome: String,
        meaning: String
    ) {}

    fun declareApiCapabilities(
        producer: String,
        declarations: List<TelemetryApiCapability>
    ): Boolean = false

    fun apiStarted(producer: String, apiName: String): String? = null

    fun apiCompleted(id: String, producer: String, apiName: String, outcome: String) {}

    fun setCaptureMode(mode: String): Boolean = false

    fun migrationStatus(): Map<String, Any> = emptyMap()

    fun expireApiOperations() {}
}
