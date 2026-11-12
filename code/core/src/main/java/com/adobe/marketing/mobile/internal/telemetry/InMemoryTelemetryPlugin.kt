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

/** Local-only proof of concept; policy inputs are not a production consent integration. */
class InMemoryTelemetryPlugin(private val capacity: Int = 256) : ApiCallRecorder {
    private val lock = Any()
    private val calls = ArrayList<ApiCall>()
    private var collectionAllowed = false
    private var droppedCalls = 0L

    init {
        require(capacity > 0) { "Capacity must be positive." }
    }

    fun updateCollectionPolicy(enabled: Boolean, consentGranted: Boolean) {
        synchronized(lock) {
            collectionAllowed = enabled && consentGranted
            if (!collectionAllowed) {
                calls.clear()
                droppedCalls = 0
            }
        }
    }

    override fun recordApiCall(call: ApiCall) {
        synchronized(lock) {
            if (!collectionAllowed) return
            if (calls.size >= capacity) {
                droppedCalls++
                return
            }
            calls.add(call)
        }
    }

    fun snapshot(): List<ApiCall> = synchronized(lock) { calls.toList() }

    fun droppedCallCount(): Long = synchronized(lock) { droppedCalls }
}
