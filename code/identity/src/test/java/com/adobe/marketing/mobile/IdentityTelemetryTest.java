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

package com.adobe.marketing.mobile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.adobe.marketing.mobile.internal.PluginRegistry;
import com.adobe.marketing.mobile.internal.telemetry.ApiCall;
import com.adobe.marketing.mobile.internal.telemetry.InMemoryTelemetryPlugin;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class IdentityTelemetryTest {
    private InMemoryTelemetryPlugin recorder;

    @Before
    public void setup() {
        PluginRegistry.INSTANCE.clear();
        recorder = new InMemoryTelemetryPlugin(256);
        PluginRegistry.INSTANCE.addPlugin(recorder);
    }

    @After
    public void cleanup() {
        PluginRegistry.INSTANCE.clear();
    }

    @Test
    public void fixedMetadataCoversSevenSignaturesWithoutDelegationDoubleCounting() {
        recorder.updateCollectionPolicy(true, true);
        try (MockedStatic<MobileCore> ignored = Mockito.mockStatic(MobileCore.class)) {
            Map<String, String> identifiers = new HashMap<>();
            identifiers.put("privateType", "privateIdentifier");
            Identity.syncIdentifiers(identifiers);
            Identity.syncIdentifiers(identifiers, VisitorID.AuthenticationState.AUTHENTICATED);
            Identity.syncIdentifier(
                    "privateType", "privateIdentifier", VisitorID.AuthenticationState.UNKNOWN);
            Identity.appendVisitorInfoForURL("https://private.example/path", value -> {});
            Identity.getUrlVariables(value -> {});
            Identity.getIdentifiers(value -> {});
            Identity.getExperienceCloudId(value -> {});
        }
        List<ApiCall> calls = recorder.snapshot();
        assertEquals(
                Arrays.asList(
                        "syncIdentifiers",
                        "syncIdentifiers",
                        "syncIdentifier",
                        "appendVisitorInfoForURL",
                        "getUrlVariables",
                        "getIdentifiers",
                        "getExperienceCloudId"),
                calls.stream().map(ApiCall::getApiName).collect(Collectors.toList()));
        for (ApiCall call : calls) {
            assertEquals("com.adobe.module.identity", call.getExtensionName());
            assertEquals("3.0.2", call.getExtensionVersion());
        }
        assertFalse(calls.toString().contains("private"));
    }

    @Test
    public void metadataAndDefaultOffAreNotRecorded() {
        Identity.extensionVersion();
        Identity.syncIdentifier("", "privateIdentifier", VisitorID.AuthenticationState.UNKNOWN);
        assertTrue(recorder.snapshot().isEmpty());
    }
}
