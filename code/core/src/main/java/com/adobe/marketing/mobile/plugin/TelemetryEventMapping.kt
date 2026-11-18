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
 * An exact EventHub type/source selector mapped to reviewed telemetry names.
 *
 * [eventName] optionally narrows a selector to an exact hub event name. [xdmEventType] optionally
 * matches the payload's xdm.eventType; this is distinct from EventHub's [eventType].
 * [requiredKey] can distinguish Core tracking APIs by presence of "action" or "state"; their
 * values are never forwarded. No payload fields are forwarded. Duplicate enabled producer/name
 * mappings emit one record per dispatch. XDM selectors support exact values or a trailing ".*".
 */
data class TelemetryEventMapping @JvmOverloads constructor(
    val eventType: String,
    val eventSource: String,
    val producer: String,
    val telemetryEventName: String,
    val eventName: String? = null,
    val xdmEventType: String? = null,
    val requiredKey: String? = null,
    val enabled: Boolean = true,
    val retireWhenApisCovered: List<String> = emptyList(),
    val legacyFallback: Boolean = true,
    val requiredBooleanKey: String? = null,
    val forbiddenKey: String? = null
)
