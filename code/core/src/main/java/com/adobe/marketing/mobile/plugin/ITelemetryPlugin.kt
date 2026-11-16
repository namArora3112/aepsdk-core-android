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

/**
 * Optional telemetry capability registered through MobileCore.addPlugins.
 *
 * Register capture policy through TelemetryBridge.configure after installation and on policy
 * changes. Core invokes this sink on a bounded worker, never on the business dispatch thread.
 * Implementations must not dispatch telemetry back to EventHub. They own permission checks at
 * persistence/upload time and purge of persisted records on denial.
 * The generation returned by configure must also be checked under the sink's persistence lock,
 * preventing a callback already in progress from writing after disable/re-enable.
 */
interface ITelemetryPlugin : IAepPlugin {
    /** Receives only catalog-owned names and event correlation metadata, never customer data. */
    fun recordEvent(producer: String, eventName: String, eventId: String, timestamp: Long, generation: Long)

    /** Direct operation metadata. Core owns the correlation ID and measured duration. */
    fun recordOperation(
        operationId: String,
        producer: String,
        eventName: String,
        phase: String,
        outcome: String?,
        meaning: String?,
        durationMs: Long?,
        generation: Long
    )
}
