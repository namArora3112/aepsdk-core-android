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

package com.adobe.marketing.mobile.telemetry

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.adobe.marketing.mobile.AdobeCallbackWithError
import com.adobe.marketing.mobile.AdobeError
import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.EventSource
import com.adobe.marketing.mobile.internal.telemetry.ApiCall
import com.adobe.marketing.mobile.internal.telemetry.SdkTelemetry
import com.adobe.marketing.mobile.internal.telemetry.TelemetrySnapshot
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
class SdkTelemetryPluginTest {
    private lateinit var application: Application
    private lateinit var plugin: SdkTelemetryPlugin
    private val policy = AtomicReference(
        TelemetrySnapshot(
            true, "TEST@AdobeOrg", "optedin", "y",
            mapOf(SdkTelemetry.CORE to "1", "com.adobe.edge" to "2", "com.adobe.messaging" to "3"),
            "00000000-0000-0000-0000-000000000001", "https://example.invalid/ee/v1/interact"
        )
    )
    private data class Request(val event: Event, val callback: AdobeCallbackWithError<Event>)
    private val requests = CopyOnWriteArrayList<Request>()

    @Before
    fun setup() {
        application = RuntimeEnvironment.getApplication()
        File(application.noBackupFilesDir, "aep-sdk-telemetry").deleteRecursively()
        createPlugin()
    }

    private fun createPlugin() {
        plugin = SdkTelemetryPlugin("_cjmstage", 1024).also {
            it.applicationProvider = { application }
            it.snapshotProvider = { policy.get() }
            it.dispatcher = { event, callback -> requests.add(Request(event, callback)) }
        }
    }

    @After
    fun cleanup() {
        plugin.close()
        assertTrue(plugin.awaitClosed())
    }

    private fun counters(): Map<String, Long> {
        val result = AtomicReference<Map<String, Long>>()
        val latch = CountDownLatch(1)
        plugin.diagnostics { result.set(it); latch.countDown() }
        assertTrue(latch.await(5, TimeUnit.SECONDS))
        return result.get()
    }

    private fun settle() { repeat(3) { counters() } }

    private fun policy(snapshot: TelemetrySnapshot) {
        policy.set(snapshot)
        plugin.invalidate()
        settle()
    }

    private fun record(count: Int = 1) {
        repeat(count) {
            plugin.recordApiCall(
                ApiCall(
                    "com.adobe.messaging", "wrong-producer-version",
                    "Messaging.refreshInAppMessages", System.currentTimeMillis()
                )
            )
        }
        settle()
    }

    private fun respond(request: Request, outcome: String) {
        request.callback.call(
            Event.Builder("response", SdkTelemetry.EVENT_TYPE, EventSource.RESPONSE_CONTENT)
                .inResponseToEvent(request.event).setEventData(mapOf("outcome" to outcome)).build()
        )
        settle()
    }

    @Test
    fun defaultOffDoesNotCreateDirectoryOrBufferCalls() {
        policy.set(policy.get().copy(enabled = false))
        plugin.onRegistered()
        settle()
        record(20)
        assertFalse(File(application.noBackupFilesDir, "aep-sdk-telemetry").exists())
        assertEquals(0L, counters()["buffered"])
        assertTrue(requests.isEmpty())
    }

    @Test
    fun registrationCompletionNotElapsedTimeStartsAdoption() {
        policy.set(policy.get().copy(registrationComplete = false))
        plugin.onRegistered()
        settle()
        record(20)
        assertTrue(requests.isEmpty())
        assertFalse(File(application.noBackupFilesDir, "aep-sdk-telemetry").exists())
        policy(policy.get().copy(registrationComplete = true))
        assertEquals(1, requests.size)
        assertEquals("sdk.adoption", JSONObject(requests.single().event.eventData!!["payload"] as String).getString("eventType"))
        assertEquals(1L, counters()["entries"]) // appStart only; no unknown-policy calls replayed.
    }

    @Test
    fun absentAdapterOrDestinationStillCapturesOfflineWithoutAttempts() {
        policy.set(policy.get().copy(telemetryEndpoint = null))
        plugin.onRegistered()
        settle()
        record(20)
        assertTrue(requests.isEmpty())
        assertEquals(0L, counters()["networkAttempts"])
        assertTrue(counters()["entries"]!! >= 20)
        assertTrue(policy.get().permitted)
    }

    @Test
    fun unknownAtRestartPreservesOutboxButAdmitsNothing() {
        plugin.onRegistered()
        settle()
        record(3)
        val before = counters()["entries"]
        plugin.close()
        assertTrue(plugin.awaitClosed())
        policy.set(policy.get().copy(enabled = false, configurationReady = false, collect = null, privacy = null))
        createPlugin()
        plugin.onRegistered()
        settle()
        record(20)
        // Existing data is not even opened while states are still loading.
        assertTrue(File(application.noBackupFilesDir, "aep-sdk-telemetry/outbox.db").exists())
        policy(policy.get().copy(enabled = true, configurationReady = true, collect = "y", privacy = "optedin"))
        assertEquals(before!! + 1, counters()["entries"]) // only the new process appStart.
    }

    @Test
    fun deniedAtRestartPurgesExistingOutboxAndPreservesInstallation() {
        plugin.onRegistered()
        settle()
        record(3)
        val uuidFile = File(application.noBackupFilesDir, "aep-sdk-telemetry/install.uuid")
        val uuid = uuidFile.readText()
        val old = requests.single()
        plugin.close()
        assertTrue(plugin.awaitClosed())
        policy.set(policy.get().copy(collect = "n"))
        createPlugin()
        plugin.onRegistered()
        settle()
        assertEquals(0L, counters()["entries"])
        assertEquals(0L, counters()["batches"])
        old.callback.call(
            Event.Builder("late", SdkTelemetry.EVENT_TYPE, EventSource.RESPONSE_CONTENT)
                .inResponseToEvent(old.event).setEventData(mapOf("outcome" to "accepted")).build()
        )
        settle()
        assertEquals(0L, counters()["batches"])
        assertEquals(uuid, uuidFile.readText())
    }

    @Test
    fun revocationFencesInflightSuccessAndNoDeniedCallsLeakAfterGrant() {
        plugin.onRegistered()
        settle()
        val old = requests.single()
        record(20)
        policy(policy.get().copy(collect = "n"))
        record(20)
        respond(old, "accepted")
        assertEquals(0L, counters()["entries"])
        assertEquals(0L, counters()["batches"])
        policy(policy.get().copy(collect = "y"))
        assertEquals(0L, counters()["entries"])
        assertEquals(2, requests.size) // a new adoption, never resurrecting the purged one.
    }

    @Test
    fun usageWaitsForAdoptionAckAndRetainsSavedInventoryNotCallerVersion() {
        plugin.onRegistered()
        settle()
        val adoption = requests.single()
        record(20)
        assertEquals(1, requests.size)
        assertTrue(counters()["batches"]!! > 1)
        respond(adoption, "accepted")
        assertEquals(2, requests.size)
        val usage = JSONObject(requests.last().event.eventData!!["payload"] as String)
        assertEquals("sdk.usage", usage.getString("eventType"))
        val tenant = usage.getJSONObject("_cjmstage")
        val versions = tenant.getJSONArray("sdkVersions")
        assertEquals("com.adobe.messaging", versions.getJSONObject(1).getString("name"))
        assertEquals("3", versions.getJSONObject(1).getString("version"))
        assertFalse(usage.toString().contains("wrong-producer-version"))
        assertTrue(tenant.getJSONObject("metadata").getJSONObject("application").has("version"))
        val records = tenant.getJSONObject("telemetry").getJSONArray("events")
        assertEquals("appStart", records.getJSONObject(0).getString("apiName"))
        assertEquals("refreshInAppMessages", records.getJSONObject(1).getString("apiName"))
    }

    @Test
    fun historicalDenialCannotBeHiddenByRapidGrantOrLateSuccess() {
        plugin.onRegistered()
        settle()
        record(3)
        val old = requests.single()
        // Current state is already granted again, but the versioned denial still must purge.
        plugin.eventSnapshotProvider = { policy.get().copy(collect = "n") }
        val denied = Event.Builder("deny", "policy", "sharedstate").build()
        plugin.policyChanged(denied)
        settle()
        assertEquals(0L, counters()["entries"])
        assertEquals(2, requests.size)
        respond(old, "accepted")
        assertEquals(1L, counters()["batches"]) // only the replacement adoption survives.
    }

    @Test
    fun timeoutRetainsSavedBatchAndDoesNotImmediatelyRetry() {
        plugin.onRegistered()
        settle()
        requests.single().callback.fail(AdobeError.CALLBACK_TIMEOUT)
        settle()
        assertEquals(1, requests.size)
        assertEquals(1L, counters()["batches"])
        assertEquals(1L, counters()["networkAttempts"])
    }

    @Test
    fun recorderIsBoundedAndNeverWaitsForBusyWorker() {
        plugin.onRegistered()
        settle()
        val entered = CountDownLatch(1)
        val release = CountDownLatch(1)
        plugin.diagnostics {
            entered.countDown()
            release.await(5, TimeUnit.SECONDS)
        }
        assertTrue(entered.await(5, TimeUnit.SECONDS))
        try {
            repeat(300) {
                plugin.recordApiCall(ApiCall("com.adobe.messaging", "3", "refreshInAppMessages", System.currentTimeMillis()))
            }
        } finally {
            release.countDown()
        }
        settle()
        assertEquals(44L, counters()["dropped"])
        assertEquals(257L, counters()["entries"]) // bounded 256 calls plus appStart.
    }

    @Test
    fun workerExceptionsAreContainedAndRecoveryDoesNotEscapeApiFacade() {
        plugin.snapshotProvider = { throw IllegalStateException("unavailable") }
        plugin.onRegistered()
        settle()
        record(20)
        assertEquals(0L, counters()["ready"])
        assertTrue(requests.isEmpty())
        plugin.snapshotProvider = { policy.get() }
        plugin.invalidate()
        settle()
        assertEquals(1L, counters()["ready"])
        assertEquals(1, requests.size)
    }

    private fun deniedRestartWithDamagedUuid(corrupt: Boolean) {
        plugin.onRegistered()
        settle()
        record(3)
        plugin.close()
        assertTrue(plugin.awaitClosed())
        val uuid = File(application.noBackupFilesDir, "aep-sdk-telemetry/install.uuid")
        if (corrupt) uuid.writeText("not-a-uuid") else assertTrue(uuid.delete())
        policy.set(policy.get().copy(collect = "n"))
        createPlugin()
        plugin.onRegistered()
        settle()
        assertEquals(0L, counters()["entries"])
        assertEquals(0L, counters()["batches"])
        if (corrupt) assertEquals("not-a-uuid", uuid.readText()) else assertFalse(uuid.exists())
        assertEquals(0L, counters()["workerFailures"])
    }

    @Test
    fun deniedExistingOutboxWithoutUuidPurgesWithoutCreatingIdentity() = deniedRestartWithDamagedUuid(false)

    @Test
    fun deniedExistingOutboxWithCorruptUuidPurgesWithoutReadingOrReplacingIdentity() = deniedRestartWithDamagedUuid(true)

    @Test
    fun failedSqliteDenialPurgeCannotBeBypassedByALaterGrant() {
        plugin.onRegistered()
        settle()
        record(3)
        val old = requests.single()
        val file = File(application.noBackupFilesDir, "aep-sdk-telemetry/outbox.db")
        SQLiteDatabase.openOrCreateDatabase(file, null).use {
            it.execSQL("CREATE TRIGGER reject_purge BEFORE DELETE ON batches BEGIN SELECT RAISE(FAIL,'test purge failure'); END")
        }
        policy(policy.get().copy(collect = "n"))
        assertEquals(0L, counters()["ready"])
        policy(policy.get().copy(collect = "y"))
        assertEquals(0L, counters()["ready"])
        assertEquals(1, requests.size)
        SQLiteDatabase.openOrCreateDatabase(file, null).use { it.execSQL("DROP TRIGGER reject_purge") }
        plugin.invalidate()
        settle()
        assertEquals(0L, counters()["entries"])
        assertEquals(1L, counters()["ready"])
        assertEquals(2, requests.size)
        respond(old, "accepted")
        assertEquals(1L, counters()["batches"])
    }
}
