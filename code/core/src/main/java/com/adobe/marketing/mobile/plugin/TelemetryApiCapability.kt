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

/** POC contract supplied by an SDK, not inferred from its version or EventHub traffic. */
data class TelemetryApiCapability @JvmOverloads constructor(
    val apiName: String,
    val extensionVersion: String,
    val contractVersion: Int = 1,
    val outcomes: Set<String> = emptySet(),
    val outcomeContract: String? = null,
    val observationTtlMs: Long = 60_000
)

/** Approved API metadata only; an expired observation is unknown, not an API timeout. */
data class TelemetryApiRecord(
    val operationId: String,
    val producer: String,
    val apiName: String,
    val phase: String,
    val extensionVersion: String,
    val contractVersion: Int,
    val outcomeContract: String?,
    val coverage: String,
    val captureMode: String,
    val outcome: String? = null,
    val meaning: String? = null,
    val durationMs: Long? = null
)

/** Separate optional sink keeps the original event/operation plugin contract intact. */
interface IApiTelemetryPlugin : ITelemetryPlugin {
    fun recordApi(record: TelemetryApiRecord, generation: Long)
}
