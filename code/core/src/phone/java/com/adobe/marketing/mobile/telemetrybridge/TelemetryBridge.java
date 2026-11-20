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

package com.adobe.marketing.mobile.telemetrybridge;

import com.adobe.marketing.mobile.internal.eventhub.EventHub;
import com.adobe.marketing.mobile.internal.eventhub.EventHubTelemetry;
import com.adobe.marketing.mobile.plugin.TelemetryApiCapability;
import com.adobe.marketing.mobile.plugin.TelemetryEventMapping;
import com.adobe.marketing.mobile.plugin.ITelemetryPlugin;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Optional typed direct hooks for operations not represented by EventHub events. */
public final class TelemetryBridge {
    private TelemetryBridge() {}

    /**
     * Compatibility entry for older POCs. Policy and catalog matching are not
     * performed by Core; metadata-mapper plugins are the supported handoff.
     */
    public static long configure(
            final ITelemetryPlugin plugin,
            final boolean enabled,
            final List<TelemetryEventMapping> mappings) {
        if (!(plugin instanceof com.adobe.marketing.mobile.plugin.IEventTelemetryPlugin)) {
            if (enabled) {
                throw new IllegalArgumentException(
                        "Telemetry event mapper plugin is required; Core does not match catalogs");
            }
            return EventHubTelemetry.INSTANCE.shutdownWithGeneration();
        }
        return EventHubTelemetry.INSTANCE.configure(
                (com.adobe.marketing.mobile.plugin.IEventTelemetryPlugin) plugin, enabled);
    }

    public static Map<String, String> registeredSdkVersions() {
        try {
            return EventHub.Companion.getShared().registeredVersionsForTelemetry();
        } catch (Exception | LinkageError ignored) {
            return Collections.emptyMap();
        }
    }

    public static long configureEventMapper(
            final com.adobe.marketing.mobile.plugin.IEventTelemetryPlugin plugin,
            final boolean enabled) {
        return EventHubTelemetry.INSTANCE.configure(plugin, enabled);
    }

    public static String operationStarted(final String producer, final String eventName) {
        try {
            return EventHubTelemetry.INSTANCE.operationStarted(producer, eventName);
        } catch (Exception | LinkageError ignored) {
            return null;
        }
    }

    public static boolean declareApiCapabilities(
            final String producer, final List<TelemetryApiCapability> capabilities) {
        try {
            return EventHubTelemetry.INSTANCE.declareApiCapabilities(producer, capabilities);
        } catch (Exception | LinkageError ignored) {
            return false;
        }
    }

    public static String apiStarted(final String producer, final String apiName) {
        try {
            return EventHubTelemetry.INSTANCE.apiStarted(producer, apiName);
        } catch (Exception | LinkageError ignored) {
            return null;
        }
    }

    public static void apiCompleted(
            final String id, final String producer, final String apiName, final String outcome) {
        if (id == null) return;
        try {
            EventHubTelemetry.INSTANCE.apiCompleted(id, producer, apiName, outcome);
        } catch (Exception | LinkageError ignored) {
            // Optional instrumentation never changes business behavior.
        }
    }

    public static boolean setCaptureMode(final String mode) {
        return EventHubTelemetry.INSTANCE.setCaptureMode(mode);
    }

    public static Map<String, Object> migrationStatus() {
        return EventHubTelemetry.INSTANCE.migrationStatus();
    }

    public static void expireApiOperations() {
        EventHubTelemetry.INSTANCE.expireApiOperations();
    }

    public static void operationCompleted(
            final String operationId,
            final String producer,
            final String eventName,
            final String outcome,
            final String meaning) {
        if (operationId == null) {
            return;
        }
        try {
            EventHubTelemetry.INSTANCE.operationCompleted(
                    operationId, producer, eventName, outcome, meaning);
        } catch (Exception | LinkageError ignored) {
            // Telemetry failures must not change the business operation.
        }
    }
}
