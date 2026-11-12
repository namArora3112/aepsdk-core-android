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

package com.adobe.marketing.mobile.telemetry.internal

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.adobe.marketing.mobile.internal.telemetry.ApiCall
import com.adobe.marketing.mobile.internal.telemetry.SdkTelemetry
import com.adobe.marketing.mobile.internal.telemetry.TelemetrySnapshot
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
class TelemetryStoreTest {
    private lateinit var application: Application
    private lateinit var store: TelemetryStore
    private val now = 1_800_000_000_000L
    private val snapshot = TelemetrySnapshot(
        true, "TEST@AdobeOrg", "optedin", "y",
        mapOf(SdkTelemetry.CORE to "1", "com.adobe.messaging" to "2")
    )

    @Before
    fun setup() {
        application = RuntimeEnvironment.getApplication()
        File(application.noBackupFilesDir, "aep-sdk-telemetry").deleteRecursively()
        store = TelemetryStore(application, "_cjmstage", 1024)
    }

    @After
    fun cleanup() = store.close()

    private fun context(version: String = "v1", time: Long = now): TelemetryStore.ActiveContext {
        val adoption = TelemetryPayload.adoption(application, "_cjmstage", store.installId, snapshot, time)
        adoption.getJSONObject("_cjmstage").getJSONObject("metadata")
            .getJSONObject("application").put("version", version)
        adoption.getJSONObject("_cjmstage").getJSONArray("sdkVersions").getJSONObject(0).put("version", version)
        return store.context(version, "TEST@AdobeOrg", adoption, time)
    }

    private fun entry(time: Long = now): JSONObject =
        TelemetryPayload.record(ApiCall(SdkTelemetry.CORE, "ignored", "trackAction", time))!!

    private fun acceptAdoption(time: Long = now) {
        val batch = store.beginAttempt(time)!!
        assertEquals("sdk.adoption", JSONObject(batch.payload).getString("eventType"))
        store.complete(batch, "accepted", time)
    }

    private fun tenant(batch: TelemetryStore.Batch) = JSONObject(batch.payload).getJSONObject("_cjmstage")
    private fun events(batch: TelemetryStore.Batch) = tenant(batch).getJSONObject("telemetry").getJSONArray("events")

    @Test
    fun sizeOnlyNeverSealsForAgeCountOrUpgrade() {
        val active = context()
        acceptAdoption()
        store.append(active.id, listOf(entry()))
        store.seal(now + 6 * 24 * 60 * 60_000L)
        assertNull(store.beginAttempt(now + 6 * 24 * 60 * 60_000L))
        context("v2")
        store.seal(now + 60_000)
        acceptAdoption(now + 60_000)
        assertNull(store.beginAttempt(now + 60_000))
        // Count is an envelope cap, not a threshold: use a larger configured byte target.
        store.close()
        store = TelemetryStore(application, "_cjmstage", 48 * 1024)
        val next = context("v2")
        store.append(next.id, List(51) { entry() })
        store.seal(now + 60_000)
        assertNull(store.beginAttempt(now + 60_000))
    }

    @Test
    fun crossContextThresholdDrainsPartialContextsWithFullSavedMetadata() {
        val first = context()
        acceptAdoption()
        store.append(first.id, List(5) { entry() })
        store.seal(now)
        assertNull(store.beginAttempt(now))
        val second = context("v2", now + 1)
        store.append(second.id, List(5) { entry(now + 1) })
        store.seal(now + 1)
        acceptAdoption(now + 1)
        val old = store.beginAttempt(now + 1)!!
        assertEquals(5, events(old).length())
        assertEquals("v1", tenant(old).getJSONObject("metadata").getJSONObject("application").getString("version"))
        assertEquals("v1", tenant(old).getJSONArray("sdkVersions").getJSONObject(0).getString("version"))
        assertTrue(tenant(old).getJSONObject("metadata").has("permissions"))
        assertTrue(tenant(old).getJSONObject("metadata").has("device"))
        store.complete(old, "accepted", now + 1)
        val newer = store.beginAttempt(now + 1)!!
        assertEquals(5, events(newer).length())
        assertEquals("v2", tenant(newer).getJSONObject("metadata").getJSONObject("application").getString("version"))
    }

    @Test
    fun exactByteBoundaryUsesPendingUtf8AndPreservesRemainder() {
        val active = context()
        acceptAdoption()
        val one = entry()
        val size = one.toString().toByteArray(Charsets.UTF_8).size
        val number = (1024 - 1) / size
        store.append(active.id, List(number) { one })
        store.seal(now)
        assertNull(store.beginAttempt(now))
        store.append(active.id, listOf(one))
        store.seal(now)
        val batch = store.beginAttempt(now)!!
        assertEquals(number + 1, events(batch).length())
        store.append(active.id, listOf(one))
        store.complete(batch, "accepted", now)
        assertEquals(1L, store.diagnostics()["entries"])
        store.seal(now + 100_000)
        assertNull(store.beginAttempt(now + 100_000))
    }

    @Test
    fun adoptionGatesUsageAndUnchangedRestartDoesNotDuplicateAdoption() {
        val active = context()
        store.append(active.id, List(12) { entry() })
        store.seal(now)
        val adoption = store.beginAttempt(now)!!
        assertNull(store.beginAttempt(now))
        store.complete(adoption, "accepted", now)
        val usage = store.beginAttempt(now)!!
        store.complete(usage, "accepted", now)
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertEquals(active.id, context().id)
        assertNull(store.beginAttempt(now + 60_000))
        assertTrue(context("v2").upgraded)
        assertTrue(context("v1").upgraded)
    }

    @Test
    fun retryAndRestartKeepPayloadMembershipAndFenceOldLease() {
        val active = context()
        acceptAdoption()
        store.append(active.id, List(12) { entry() })
        store.seal(now)
        val first = store.beginAttempt(now)!!
        store.complete(first, "retry", now)
        assertNull(store.beginAttempt(now + 1))
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        val second = store.beginAttempt(now + 60_000)!!
        assertEquals(first.id, second.id)
        assertEquals(first.payload, second.payload)
        assertEquals(first.attempt + 1, second.attempt)
        assertNotEquals(first.lease, second.lease)
        store.complete(first, "accepted", now + 60_000)
        assertEquals(12L, store.diagnostics()["entries"])
        store.complete(second, "accepted", now + 60_000)
        assertEquals(0L, store.diagnostics()["entries"])
    }

    @Test
    fun abandonedLeaseRecoversWithBackoffAndExclusiveOwnership() {
        context()
        val first = store.beginAttempt(now)!!
        try {
            TelemetryStore(application, "_cjmstage", 1024)
            fail("A second owner must not open the database")
        } catch (_: Exception) { /* Expected exclusive owner. */ }
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertNull(store.beginAttempt(now + 1))
        val recovered = store.beginAttempt(now + 60_000)!!
        assertEquals(first.payload, recovered.payload)
        assertNotEquals(first.lease, recovered.lease)
        store.complete(first, "accepted", now + 60_000)
        assertEquals(1L, store.diagnostics()["batches"])
    }

    @Test
    fun blockedDoesNotConsumeBudgetOrRetryExponentAndUsesFreshLease() {
        context()
        var old: TelemetryStore.Batch? = null
        repeat(15) { index ->
            val time = now + index * 6_000
            val next = store.beginAttempt(time)!!
            assertEquals(1, next.attempt)
            old?.let { store.complete(it, "accepted", time) }
            assertEquals(1L, store.diagnostics()["batches"])
            store.complete(next, "blocked", time)
            assertEquals(0L, store.diagnostics()["networkAttempts"])
            old = next
        }
    }

    @Test
    fun retryAfterPersistsAndNeverRetriesEarlyAcrossRestart() {
        context()
        val first = store.beginAttempt(now)!!
        store.complete(first, "retry", now, 120_000L)
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertNull(store.beginAttempt(now + 119_999))
        assertNotNull(store.beginAttempt(now + 120_000))
    }

    @Test
    fun blockedRetryAfterIsHonoredWithoutBudget() {
        context()
        val first = store.beginAttempt(now)!!
        store.complete(first, "blocked", now, 90_000)
        assertNull(store.beginAttempt(now + 89_999))
        assertEquals(1, store.beginAttempt(now + 90_000)!!.attempt)
    }

    @Test
    fun permanentAdoptionCannotStarveNewerContext() {
        val first = context()
        store.append(first.id, List(12) { entry() })
        store.complete(store.beginAttempt(now)!!, "drop", now)
        assertFalse(store.hasContext(first.id))
        assertEquals(0, store.append(first.id, listOf(entry())))
        context("v2", now + 1)
        acceptAdoption(now + 1)
        assertEquals(1L, store.diagnostics()["quarantined"])
    }

    @Test
    fun repeatedRealFailuresQuarantineAtBoundAndExpire() {
        context()
        repeat(TelemetryStore.MAX_ATTEMPTS) {
            val time = now + it * 5_000_000L
            val batch = store.beginAttempt(time)!!
            assertEquals(it + 1, batch.attempt)
            store.complete(batch, "retry", time)
        }
        assertEquals(1L, store.diagnostics()["quarantined"])
        assertNull(store.beginAttempt(now + 100_000_000))
        store.expire(now + TelemetryStore.RETENTION_MS + 1)
        assertEquals(0L, store.diagnostics()["batches"])
    }

    @Test
    fun purgeCannotBeUndoneByLateSuccessAndDoesNotCreateUuid() {
        // A denied-start open/purge alone creates no installation identifier.
        store.purgeUnsent()
        assertFalse(File(application.noBackupFilesDir, "aep-sdk-telemetry/install.uuid").exists())
        val install = store.installId
        val active = context()
        val inFlight = store.beginAttempt(now)!!
        store.append(active.id, listOf(entry()))
        store.purgeUnsent()
        store.complete(inFlight, "accepted", now)
        assertEquals(0L, store.diagnostics()["entries"])
        assertNull(store.beginAttempt(now))
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertEquals(install, store.installId)
    }

    @Test
    fun fullWireEnvelopeNotJustEntriesIsCappedInUtf8() {
        val active = context()
        acceptAdoption()
        // Synthetic non-production large records exercise UTF8 boundaries.
        store.append(active.id, List(12) { entry().put("testValue", "é".repeat(4_000)) })
        store.seal(now)
        val batch = store.beginAttempt(now)!!
        assertTrue(events(batch).length() < 12)
        val payload = JSONObject(batch.payload)
        assertTrue(TelemetryPayload.wireBytes(payload) <= TelemetryStore.MAX_BATCH_BYTES)
        assertTrue(TelemetryPayload.wireBytes(payload) > batch.payload.toByteArray(Charsets.UTF_8).size)
    }

    @Test
    fun recordQuotaAndRetentionAreBounded() {
        val active = context()
        assertEquals(5_000, store.append(active.id, List(5_001) { entry() }))
        store.expire(now + TelemetryStore.RETENTION_MS + 1)
        assertEquals(0L, store.diagnostics()["entries"])
    }

    @Test
    fun policyAndFixedMetadataSchemaAreDefaultOffAndExtensible() {
        assertTrue(snapshot.permitted)
        assertFalse(snapshot.copy(collect = null).permitted)
        assertFalse(snapshot.copy(privacy = null).permitted)
        assertFalse(snapshot.copy(privacy = "unknown").permitted)
        assertFalse(snapshot.copy(registrationComplete = false).permitted)
        assertFalse(snapshot.copy(enabled = false).permitted)
        assertTrue(snapshot.copy(enabled = false).privacyDenied)
        assertFalse(snapshot.copy(enabled = false, configurationReady = false).privacyDenied)
        assertTrue(snapshot.copy(collect = "n", configurationReady = false).privacyDenied)
        val call = ApiCall("com.adobe.target", "ignored-version", "Target.prefetchContent", now)
        val json = TelemetryPayload.record(call)!!
        assertEquals("Target.prefetchContent", json.getString("apiName"))
        assertEquals("API_CALL", json.getString("apiEventType"))
        assertEquals(now, json.getLong("timestamp"))
        assertFalse(json.has("errorName"))
        assertNull(TelemetryPayload.record(call.copy(apiName = "contains customer argument")))
        assertNull(TelemetryPayload.record(call.copy(extensionName = "customer.profile")))
        assertNull(TelemetryPayload.record(call.copy(apiName = "x".repeat(200))))
    }

    @Test
    fun failureDimensionsAreTightlyAllowlistedAndNotPublicApiAttribution() {
        val failure = TelemetryPayload.failure("personalization.request", "EXEG-0003-400", "Invalid datastream ID", now)!!
        assertEquals("API_FAIL", failure.getString("apiEventType"))
        assertEquals("personalization.request", failure.getString("apiName"))
        assertEquals("com.adobe.edge", failure.getString("extension"))
        assertEquals("EXEG-0003-400", failure.getString("errorCode"))
        assertEquals("Invalid datastream ID", failure.getString("errorName"))
        assertNull(TelemetryPayload.failure("refreshInAppMessages", "EXEG-0003-400", "Invalid datastream ID", now))
        assertNull(TelemetryPayload.failure("personalization.request", "EXEG-0003-400", "Customer text", now))
        assertNull(TelemetryPayload.failure("personalization.request", "customer code", "Invalid datastream ID", now))
    }

    @Test
    fun installationIdentitySurvivesUpgradeButChangesAfterApplicationDataClear() {
        val first = store.installId
        context()
        context("v2")
        store.close()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertEquals(first, store.installId)
        store.close()
        File(application.noBackupFilesDir, "aep-sdk-telemetry").deleteRecursively()
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertNotEquals(first, store.installId)
    }

    @Test
    fun referenceSchemaMigrationDoesNotFabricateMissingApplicationMetadata() {
        val install = store.installId
        context()
        store.close()
        // The pre-release reference used the same four table names and user_version=1.
        SQLiteDatabase.openOrCreateDatabase(File(application.noBackupFilesDir, "aep-sdk-telemetry/outbox.db"), null).use {
            it.version = 1
        }
        store = TelemetryStore(application, "_cjmstage", 1024)
        assertEquals(install, store.installId)
        assertEquals(0L, store.diagnostics()["batches"])
        context()
        acceptAdoption()
    }

    @Test
    fun onlyKnownMessagingFacadePrefixIsNormalizedAndInstanceApiNamesStayDistinct() {
        val call = ApiCall("com.adobe.messaging", "3", "Messaging.refreshInAppMessages", now)
        assertEquals("refreshInAppMessages", TelemetryPayload.record(call)!!.getString("apiName"))
        for (name in listOf("Message.track", "PropositionItem.track", "ContentCard.track", "Nested.Class.track")) {
            assertEquals(name, TelemetryPayload.record(call.copy(apiName = name))!!.getString("apiName"))
        }
        assertEquals(
            "Messaging.refreshInAppMessages",
            TelemetryPayload.record(call.copy(extensionName = "com.adobe.other"))!!.getString("apiName")
        )
        assertNull(TelemetryPayload.record(call.copy(apiName = "Message..track")))
    }

    @Test
    fun fiftyEntryEnvelopeCapStillDrainsTheFinalPartialAfterSizeTrigger() {
        val active = context()
        acceptAdoption()
        store.append(active.id, List(51) { entry() })
        store.seal(now)
        val full = store.beginAttempt(now)!!
        assertEquals(50, events(full).length())
        store.complete(full, "accepted", now)
        val partial = store.beginAttempt(now)!!
        assertEquals(1, events(partial).length())
        store.complete(partial, "accepted", now)
        assertEquals(0L, store.diagnostics()["entries"])
    }

    private fun exactEdgeBytes(payload: JSONObject): Int =
        JSONObject().put("xdm", JSONObject().put("identityMap", JSONObject()))
            .put("events", JSONArray().put(JSONObject().put("xdm", payload)))
            .toString().toByteArray(Charsets.UTF_8).size

    @Test
    fun wireSizeExactlyMatchesRootIdentityMapEnvelopeWithoutMutatingXdm() {
        val payload = JSONObject().put("eventType", "sdk.usage").put("_cjmstage", JSONObject().put("value", "é"))
        assertEquals(exactEdgeBytes(payload), TelemetryPayload.wireBytes(payload))
        assertFalse(payload.has("identityMap"))
    }

    @Test
    fun rootWrapperBoundaryRejectsTwoRecordsThatWouldExceedLimitByOneByte() {
        val adoption = TelemetryPayload.adoption(application, "_cjmstage", store.installId, snapshot, now)
        val active = store.context("wire-limit", "TEST@AdobeOrg", adoption, now)
        acceptAdoption()
        fun candidate(records: List<JSONObject>): JSONObject {
            val payload = JSONObject(adoption.toString()).put("_id", "00000000-0000-0000-0000-000000000000")
                .put("eventType", "sdk.usage").put("timestamp", TelemetryPayload.timestamp(now))
            payload.getJSONObject("_cjmstage").put("telemetry", JSONObject().put("events", JSONArray(records)))
            return payload
        }
        val empty = listOf(entry().put("padding", ""), entry().put("padding", ""))
        val padding = TelemetryStore.MAX_BATCH_BYTES + 1 - exactEdgeBytes(candidate(empty))
        val records = listOf(
            entry().put("padding", "a".repeat(padding / 2)),
            entry().put("padding", "a".repeat(padding - padding / 2))
        )
        assertEquals(TelemetryStore.MAX_BATCH_BYTES + 1, exactEdgeBytes(candidate(records)))
        assertEquals(2, store.append(active.id, records))
        store.seal(now)
        val first = store.beginAttempt(now)!!
        assertEquals(1, events(first).length())
        assertTrue(exactEdgeBytes(JSONObject(first.payload)) <= TelemetryStore.MAX_BATCH_BYTES)
        store.complete(first, "accepted", now)
        val second = store.beginAttempt(now)!!
        assertEquals(1, events(second).length())
        assertTrue(exactEdgeBytes(JSONObject(second.payload)) <= TelemetryStore.MAX_BATCH_BYTES)
    }

    @Test
    fun abandonedEmptyPassDoesNotTurnRestartIntoASubthresholdFlush() {
        val active = context()
        acceptAdoption()
        store.close()
        val file = File(application.noBackupFilesDir, "aep-sdk-telemetry/outbox.db")
        SQLiteDatabase.openOrCreateDatabase(file, null).use { it.execSQL("UPDATE control SET drain=1") }
        store = TelemetryStore(application, "_cjmstage", 1024)
        store.append(active.id, listOf(entry()))
        store.seal(now)
        assertNull(store.beginAttempt(now))
    }

    @Test
    fun expiryOfInterruptedPassCannotFlushFreshSubthresholdRecords() {
        val active = context()
        acceptAdoption()
        store.append(active.id, List(12) { entry() })
        store.close()
        val file = File(application.noBackupFilesDir, "aep-sdk-telemetry/outbox.db")
        SQLiteDatabase.openOrCreateDatabase(file, null).use { it.execSQL("UPDATE control SET drain=1") }
        store = TelemetryStore(application, "_cjmstage", 1024)
        val later = now + TelemetryStore.RETENTION_MS + 1
        store.expire(later)
        store.append(active.id, listOf(entry(later)))
        store.seal(later)
        assertNull(store.beginAttempt(later))
    }
}
