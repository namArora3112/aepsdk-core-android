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

import android.content.Context
import com.adobe.marketing.mobile.AdobeCallbackWithError
import com.adobe.marketing.mobile.AdobeError
import com.adobe.marketing.mobile.Event
import com.adobe.marketing.mobile.EventSource
import com.adobe.marketing.mobile.EventType
import com.adobe.marketing.mobile.MobileCore
import com.adobe.marketing.mobile.internal.telemetry.ApiCall
import com.adobe.marketing.mobile.internal.telemetry.SdkTelemetry
import com.adobe.marketing.mobile.internal.telemetry.TelemetryLifecycle
import com.adobe.marketing.mobile.internal.telemetry.TelemetrySnapshot
import com.adobe.marketing.mobile.services.Log
import com.adobe.marketing.mobile.services.ServiceProvider
import com.adobe.marketing.mobile.telemetry.internal.TelemetryPayload
import com.adobe.marketing.mobile.telemetry.internal.TelemetryStore
import org.json.JSONObject
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

/**
 * Optional, default-off SDK telemetry collector. Register explicitly with MobileCore.addPlugins.
 * The API callback only validates fixed metadata and offers to a bounded in-memory queue.
 * All shared-state reads, UUID creation, SQLite and dispatch run on the single worker.
 */
class SdkTelemetryPlugin @JvmOverloads constructor(
    private val namespace: String = "",
    private val batchTargetBytes: Int = 16 * 1024
) : TelemetryLifecycle {
    private data class Admission(val contextId: String, val epoch: Long, val versions: Map<String, String>)
    private data class Pending(val admission: Admission, val call: ApiCall? = null, val failure: JSONObject? = null)
    private data class Correlation(val admission: Admission, val type: String, val expiresAt: Long)

    private val worker = Executors.newSingleThreadScheduledExecutor {
        Thread(it, "AEPTelemetry").apply { isDaemon = true; priority = Thread.MIN_PRIORITY }
    }
    private val started = AtomicBoolean()
    private val closed = AtomicBoolean()
    private val wakeQueued = AtomicBoolean()
    private val reportedFailure = AtomicBoolean()
    private val epoch = AtomicLong()
    private val admission = AtomicReference<Admission?>()
    private val pending = ArrayBlockingQueue<Pending>(256)
    private val policyEvents = ArrayBlockingQueue<Event>(32)
    private val policyOverflow = AtomicBoolean()
    private val correlations = LinkedHashMap<String, Correlation>()
    private val dropped = AtomicLong()
    private var store: TelemetryStore? = null
    private var currentContext: String? = null
    private var currentKey: String? = null
    private var appStartRecorded = false
    private var inFlight: TelemetryStore.Batch? = null
    private var lastPolicy: TelemetrySnapshot? = null
    private var purgeRequired = false
    private lateinit var application: Context
    private lateinit var appVersion: String

    // Test seams remain internal; production always reads real shared states and uses EventHub.
    internal var snapshotProvider: () -> TelemetrySnapshot = { SdkTelemetry.snapshot() }
    internal var eventSnapshotProvider: (Event) -> TelemetrySnapshot = { SdkTelemetry.snapshot(it) }
    internal var applicationProvider: () -> Context? = {
        ServiceProvider.getInstance().appContextService.applicationContext
    }
    internal var dispatcher: (Event, AdobeCallbackWithError<Event>) -> Unit = { event, callback ->
        MobileCore.dispatchEventWithResponseCallback(event, 30_000, callback)
    }

    init {
        require(batchTargetBytes in 1024..48 * 1024) { "Batch target must be between 1024 and 49152 UTF8 bytes" }
    }

    override fun onRegistered() {
        if (closed.get() || started.get()) return
        val context = applicationProvider() ?: return
        if (!started.compareAndSet(false, true)) return
        application = context
        MobileCore.registerEventListener(EventType.CONFIGURATION, EventSource.RESPONSE_CONTENT, ::policyChanged)
        MobileCore.registerEventListener(EventType.CONSENT, EventSource.RESPONSE_CONTENT, ::policyChanged)
        MobileCore.registerEventListener(EventType.HUB, EventSource.SHARED_STATE) { event ->
            if (event.eventData?.get("stateowner") in
                setOf(SdkTelemetry.CONFIGURATION, SdkTelemetry.CONSENT, "com.adobe.module.eventhub")
            ) policyChanged(event)
        }
        MobileCore.registerEventListener(EventType.EDGE, EventSource.REQUEST_CONTENT, ::rememberRequest)
        MobileCore.registerEventListener(EventType.EDGE, EventSource.ERROR_RESPONSE_CONTENT, ::recordFailure)
        post {
            appVersion = TelemetryPayload.packageInfo(application).versionName ?: ""
            guardedTick()
        }
        // This is a ready-batch retry/policy/retention wakeup, NEVER an age-based sealing trigger.
        worker.scheduleWithFixedDelay({ guardedTick() }, 1, 1, TimeUnit.SECONDS)
    }

    override fun recordApiCall(call: ApiCall) {
        if (closed.get() || !TelemetryPayload.allowed(call)) return
        val allowed = admission.get() ?: return
        // Inventory, not a possibly inconsistent producer version, owns each context's versions.
        if (!allowed.versions.containsKey(call.extensionName)) return
        if (!pending.offer(Pending(allowed, call))) dropped.incrementAndGet()
        wake()
    }

    override fun invalidate() {
        admission.set(null)
        epoch.incrementAndGet()
        pending.clear()
        synchronized(correlations) { correlations.clear() }
        wake()
    }

    internal fun policyChanged(event: Event) {
        if (closed.get()) return
        if (!policyEvents.offer(event)) policyOverflow.set(true)
        invalidate()
    }

    private fun wake() {
        if (!started.get() || closed.get()) return
        if (wakeQueued.compareAndSet(false, true)) {
            post {
                wakeQueued.set(false)
                guardedTick()
            }
        }
    }

    private fun guardedTick() {
        if (closed.get()) return
        try {
            tick()
        } catch (_: Exception) {
            admission.set(null)
            pending.clear()
            if (reportedFailure.compareAndSet(false, true)) {
                Log.warning("AdobeExperienceSDK", "SdkTelemetryPlugin", "Optional telemetry worker unavailable.")
            }
        }
    }

    private fun applyPolicy(snapshot: TelemetrySnapshot): Boolean {
        if (snapshot.privacyDenied) {
            purgeSaved()
            return false
        }
        if (!snapshot.permitted || !namespace.matches(Regex("_[a-z0-9_]{1,63}"))) {
            if (admission.getAndSet(null) != null) epoch.incrementAndGet()
            pending.clear()
            synchronized(correlations) { correlations.clear() }
            return false
        }
        return true
    }

    private fun purgeSaved() {
        purgeRequired = true
        if (admission.getAndSet(null) != null) epoch.incrementAndGet()
        pending.clear()
        synchronized(correlations) { correlations.clear() }
        inFlight = null
        currentContext = null
        currentKey = null
        if (store == null && TelemetryStore.exists(application)) {
            store = TelemetryStore(application, namespace, batchTargetBytes)
        }
        store?.purgeUnsent()
        purgeRequired = false
    }

    private fun consumePolicyEvents() {
        // A failed denial purge must succeed before a later grant can resume any old outbox.
        if (purgeRequired) purgeSaved()
        // Resolve historical shared-state versions so a rapid deny->grant cannot hide the denial.
        // If that bounded history overflows, fail closed by discarding unsent data.
        if (policyOverflow.getAndSet(false)) purgeSaved()
        while (true) {
            val event = policyEvents.peek() ?: break
            if (eventSnapshotProvider(event).privacyDenied) purgeSaved()
            policyEvents.poll()
        }
    }

    private fun tick() {
        if (closed.get() || !::appVersion.isInitialized) return
        consumePolicyEvents()
        val snapshot = snapshotProvider()
        lastPolicy = snapshot
        if (!applyPolicy(snapshot)) return
        val now = System.currentTimeMillis()
        val observedEpoch = epoch.get()
        if (store == null) store = TelemetryStore(application, namespace, batchTargetBytes)
        val database = store ?: return
        database.expire(now)
        val org = snapshot.orgId ?: return
        val version = TelemetryPayload.versionKey(appVersion, snapshot.versions)
        val key = "$namespace:$org:$version"
        if (key != currentKey || currentContext == null) {
            admission.set(null)
            // These are already-permitted calls from the previous immutable version context.
            drain(database, observedEpoch)
            val context = database.context(
                key, org,
                TelemetryPayload.adoption(application, namespace, database.installId, snapshot, now), now
            )
            currentContext = context.id
            currentKey = key
            if (context.upgraded) database.append(context.id, listOf(TelemetryPayload.timeline("adoptionUpgrade", now)))
        }
        val context = currentContext ?: return
        if (!database.hasContext(context)) { admission.set(null); pending.clear(); return }
        if (!appStartRecorded) {
            database.append(context, listOf(TelemetryPayload.timeline("appStart", now)))
            appStartRecorded = true
        }
        if (closed.get() || observedEpoch != epoch.get()) return
        admission.set(Admission(context, observedEpoch, snapshot.versions))
        drain(database, observedEpoch)
        database.seal(now)
        // No Edge dependency and no dispatch with an absent adapter/destination.
        if (inFlight == null && snapshot.versions.containsKey("com.adobe.edge") &&
            !snapshot.telemetryConfigId.isNullOrBlank() && !snapshot.telemetryEndpoint.isNullOrBlank()
        ) {
            // Recheck policy immediately before handing an immutable saved batch to transport.
            if (applyPolicy(snapshotProvider()) && observedEpoch == epoch.get()) {
                database.beginAttempt(now, org)?.let(::send)
            }
        }
    }

    private fun drain(database: TelemetryStore, currentEpoch: Long) {
        val records = mutableListOf<Pending>()
        pending.drainTo(records)
        records.filter { it.admission.epoch == currentEpoch }.groupBy { it.admission.contextId }.forEach { (context, group) ->
            val json = group.mapNotNull { it.failure ?: it.call?.let(TelemetryPayload::record) }
            dropped.addAndGet((json.size - database.append(context, json)).toLong())
        }
    }

    private fun rememberRequest(event: Event) {
        val allowed = admission.get() ?: return
        val type = (event.eventData?.get("xdm") as? Map<*, *>)?.get("eventType") as? String ?: return
        if (!TelemetryPayload.failureTypeAllowed(type)) return
        val now = System.currentTimeMillis()
        synchronized(correlations) {
            correlations.entries.removeAll { it.value.expiresAt < now }
            if (correlations.size >= 128) correlations.remove(correlations.keys.first())
            correlations[event.uniqueIdentifier] = Correlation(allowed, type, now + 5 * 60_000)
        }
    }

    private fun recordFailure(event: Event) {
        val data = event.eventData ?: return
        val id = data["requestEventId"] as? String ?: return
        val correlation = synchronized(correlations) { correlations.remove(id) } ?: return
        val code = data["type"] as? String ?: return
        val name = data["title"] as? String ?: return
        val allowed = admission.get() ?: return
        if (correlation.expiresAt < System.currentTimeMillis() || allowed != correlation.admission) return
        val failure = TelemetryPayload.failure(correlation.type, code, name, event.timestamp) ?: return
        if (!pending.offer(Pending(allowed, failure = failure))) dropped.incrementAndGet()
        wake()
    }

    private fun send(batch: TelemetryStore.Batch) {
        inFlight = batch
        val event = Event.Builder("SDK telemetry upload", SdkTelemetry.EVENT_TYPE, EventSource.REQUEST_CONTENT)
            .setEventData(mapOf("payload" to batch.payload)).build()
        try {
            dispatcher(
                event,
                object : AdobeCallbackWithError<Event> {
                    override fun call(value: Event) {
                        if (value.type != SdkTelemetry.EVENT_TYPE || value.source != EventSource.RESPONSE_CONTENT ||
                            value.responseID != event.uniqueIdentifier
                        ) {
                            complete(batch, "retry", null)
                            return
                        }
                        val retryAfter = (value.eventData?.get("retryAfterMillis") as? Number)?.toLong()
                        complete(batch, value.eventData?.get("outcome") as? String ?: "retry", retryAfter)
                    }
                    override fun fail(error: AdobeError) = complete(batch, "retry", null)
                }
            )
        } catch (_: RuntimeException) {
            complete(batch, "blocked", null)
        }
    }

    private fun complete(batch: TelemetryStore.Batch, outcome: String, retryAfter: Long?) {
        post {
            consumePolicyEvents()
            if (closed.get() || inFlight?.lease != batch.lease) return@post
            val snapshot = snapshotProvider()
            if (!applyPolicy(snapshot)) {
                // Explicit denial purges; a transient unknown state retains the immutable batch.
                if (!snapshot.privacyDenied) store?.complete(batch, "retry", System.currentTimeMillis(), retryAfter)
                inFlight = null
                return@post
            }
            inFlight = null
            store?.complete(batch, outcome, System.currentTimeMillis(), retryAfter)
            guardedTick()
        }
    }

    /** Local counters only: no payloads, identifiers, arguments or forced flushing. */
    fun diagnostics(callback: (Map<String, Long>) -> Unit) {
        post {
            callback(
                (store?.diagnostics() ?: emptyMap()) +
                    mapOf(
                        "buffered" to pending.size.toLong(), "dropped" to dropped.get(),
                        "ready" to if (admission.get() != null) 1L else 0L,
                        "started" to if (started.get()) 1L else 0L,
                        "configurationReady" to if (lastPolicy?.configurationReady == true) 1L else 0L,
                        "registrationComplete" to if (lastPolicy?.registrationComplete == true) 1L else 0L,
                        "enabled" to if (lastPolicy?.enabled == true) 1L else 0L,
                        "collectGranted" to if (lastPolicy?.collect == "y") 1L else 0L,
                        "privacyAllowed" to if (lastPolicy?.privacy == "optedin") 1L else 0L,
                        "orgReady" to if (lastPolicy?.orgId?.matches(Regex("[A-Za-z0-9]{1,64}@AdobeOrg")) == true) 1L else 0L,
                        "inventorySize" to (lastPolicy?.versions?.size?.toLong() ?: 0L),
                        "namespaceReady" to if (namespace.matches(Regex("_[a-z0-9_]{1,63}"))) 1L else 0L,
                        "storageReady" to if (store != null) 1L else 0L,
                        "purgePending" to if (purgeRequired) 1L else 0L,
                        "workerFailures" to if (reportedFailure.get()) 1L else 0L
                    )
            )
        }
    }

    private fun post(block: () -> Unit) {
        if (closed.get()) return
        try {
            worker.execute {
                try { block() } catch (_: Exception) {
                    admission.set(null)
                    pending.clear()
                    synchronized(correlations) { correlations.clear() }
                    if (reportedFailure.compareAndSet(false, true)) {
                        Log.warning("AdobeExperienceSDK", "SdkTelemetryPlugin", "Optional telemetry worker unavailable.")
                    }
                }
            }
        } catch (_: RejectedExecutionException) { /* Reset raced with a callback. */ }
    }

    override fun close() {
        if (!closed.compareAndSet(false, true)) return
        admission.set(null)
        pending.clear()
        policyEvents.clear()
        worker.execute { store?.close(); store = null }
        worker.shutdown()
    }

    internal fun awaitClosed(): Boolean = worker.awaitTermination(5, TimeUnit.SECONDS)
}
