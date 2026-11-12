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

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.AtomicFile
import org.json.JSONArray
import org.json.JSONObject
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.channels.FileLock
import java.util.UUID
import kotlin.math.min
import kotlin.random.Random

/** Single worker, single process owner. Limits are logical safety defaults, not a physical DB cap. */
internal class TelemetryStore(
    context: Context,
    private val namespace: String,
    private val batchTargetBytes: Int = 16 * 1024
) : Closeable {
    companion object {
        const val MAX_ENTRIES = 5_000
        const val MAX_PENDING_BYTES = 5 * 1024 * 1024
        const val MAX_BATCH_BYTES = 64 * 1024
        const val MAX_BATCH_ENTRIES = 50
        const val MAX_ATTEMPTS = 10
        const val RETENTION_MS = 7 * 24 * 60 * 60_000L
        const val MAX_RETRY_AFTER_MS = RETENTION_MS
        fun exists(context: Context): Boolean =
            File(context.noBackupFilesDir, "aep-sdk-telemetry/outbox.db").exists()
    }

    data class ActiveContext(val id: String, val upgraded: Boolean)
    data class Batch(val id: String, val payload: String, val attempt: Int, val lease: String)

    private val directory = File(context.noBackupFilesDir, "aep-sdk-telemetry").also {
        if (!it.isDirectory && !it.mkdirs()) throw IOException("Telemetry directory unavailable")
    }
    private val lockChannel = RandomAccessFile(File(directory, "owner.lock"), "rw").channel
    private val lock: FileLock
    private lateinit var db: SQLiteDatabase

    // Lazy: opening an existing database to purge it while denied must never create a UUID.
    val installId: String by lazy { readInstallId() }

    init {
        require(batchTargetBytes in 1024..48 * 1024)
        try {
            lock = lockChannel.tryLock() ?: throw IOException("Another collector owns telemetry")
            db = SQLiteDatabase.openOrCreateDatabase(File(directory, "outbox.db"), null)
            db.setForeignKeyConstraintsEnabled(true)
            check(db.version <= 2) { "Unsupported telemetry database version" }
            transaction {
                if (db.version == 1) {
                    // Pre-release reference outboxes did not save immutable application metadata.
                    // Do not fabricate metadata for those records.
                    db.execSQL("DROP TABLE IF EXISTS entries")
                    db.execSQL("DROP TABLE IF EXISTS batches")
                    db.execSQL("DROP TABLE IF EXISTS contexts")
                    db.execSQL("DROP TABLE IF EXISTS attempts")
                    db.version = 0
                }
                if (db.version == 0) {
                    db.execSQL(
                        """CREATE TABLE contexts (
                        id TEXT PRIMARY KEY, version_key TEXT NOT NULL, org_id TEXT NOT NULL,
                        template TEXT NOT NULL, active INTEGER NOT NULL,
                        adopted INTEGER NOT NULL DEFAULT 0, dead INTEGER NOT NULL DEFAULT 0,
                        created_at INTEGER NOT NULL)"""
                    )
                    db.execSQL(
                        """CREATE TABLE batches (
                        id TEXT PRIMARY KEY, context_id TEXT NOT NULL REFERENCES contexts(id) ON DELETE CASCADE,
                        kind TEXT NOT NULL, payload TEXT NOT NULL, created_at INTEGER NOT NULL,
                        next_at INTEGER NOT NULL DEFAULT 0, attempt INTEGER NOT NULL DEFAULT 0,
                        lease TEXT NOT NULL DEFAULT '', in_flight INTEGER NOT NULL DEFAULT 0,
                        quarantined INTEGER NOT NULL DEFAULT 0)"""
                    )
                    db.execSQL(
                        """CREATE TABLE entries (
                        seq INTEGER PRIMARY KEY AUTOINCREMENT,
                        context_id TEXT NOT NULL REFERENCES contexts(id) ON DELETE CASCADE,
                        batch_id TEXT REFERENCES batches(id) ON DELETE CASCADE,
                        timestamp INTEGER NOT NULL, payload TEXT NOT NULL, bytes INTEGER NOT NULL)"""
                    )
                    db.execSQL("CREATE INDEX entries_context ON entries(context_id,batch_id,seq)")
                    db.execSQL("CREATE TABLE attempts (lease TEXT PRIMARY KEY, started_at INTEGER NOT NULL)")
                    db.version = 2
                }
                db.execSQL("CREATE TABLE IF NOT EXISTS control (id INTEGER PRIMARY KEY, drain INTEGER NOT NULL)")
                db.execSQL("INSERT OR IGNORE INTO control(id,drain) VALUES(1,0)")
                clearEmptyPass()
                // Crash recovery conservatively counts an abandoned dispatch as a real attempt.
                // next_at, assigned before dispatch, prevents a crash/restart retry loop.
                db.execSQL("UPDATE batches SET in_flight=0,lease=''")
            }
        } catch (exception: Exception) {
            if (::db.isInitialized) db.close()
            lockChannel.close()
            throw exception
        }
    }

    private fun readInstallId(): String {
        val file = AtomicFile(File(directory, "install.uuid"))
        if (file.baseFile.exists()) return file.openRead().use {
            UUID.fromString(it.readBytes().toString(Charsets.UTF_8)).toString()
        }
        val uuid = UUID.randomUUID().toString()
        val stream = file.startWrite()
        try {
            stream.write(uuid.toByteArray(Charsets.UTF_8))
            file.finishWrite(stream)
        } catch (exception: Exception) {
            file.failWrite(stream)
            throw exception
        }
        return uuid
    }

    /** Fingerprint, immutable full metadata and adoption are committed atomically. */
    fun context(versionKey: String, orgId: String, adoption: JSONObject, now: Long): ActiveContext = transaction {
        var previous: String? = null
        db.rawQuery("SELECT id,version_key,org_id FROM contexts WHERE active=1", null).use {
            if (it.moveToFirst()) {
                previous = it.getString(1)
                if (previous == versionKey && it.getString(2) == orgId) {
                    return@transaction ActiveContext(it.getString(0), false)
                }
            }
        }
        val id = UUID.randomUUID().toString()
        val eventId = UUID.randomUUID().toString()
        adoption.put("_id", eventId)
        val template = adoption.toString()
        check(TelemetryPayload.wireBytes(adoption) <= MAX_BATCH_BYTES)
        check(queuedBytes() + template.toByteArray(Charsets.UTF_8).size * 2 <= MAX_PENDING_BYTES)
        check(scalar("SELECT COUNT(*) FROM contexts") < 128)
        db.execSQL("UPDATE contexts SET active=0")
        insert(
            "contexts", "id" to id, "version_key" to versionKey, "org_id" to orgId,
            "template" to template, "active" to 1, "created_at" to now
        )
        insert(
            "batches", "id" to eventId, "context_id" to id, "kind" to "sdk.adoption",
            "payload" to template, "created_at" to now
        )
        ActiveContext(id, previous != null && previous != versionKey)
    }

    fun hasContext(id: String): Boolean =
        db.rawQuery("SELECT 1 FROM contexts WHERE id=? AND dead=0", arrayOf(id)).use { it.moveToFirst() }

    fun append(contextId: String, records: List<JSONObject>): Int = transaction {
        if (!hasContext(contextId)) return@transaction 0
        var count = scalar("SELECT COUNT(*) FROM entries")
        var bytes = queuedBytes()
        var accepted = 0
        records.forEach { record ->
            val payload = record.toString()
            val size = payload.toByteArray(Charsets.UTF_8).size
            // Reserve room for sealed payload duplication and full context wrappers.
            if (count < MAX_ENTRIES && size <= MAX_BATCH_BYTES / 2 && bytes + size * 2 < MAX_PENDING_BYTES) {
                insert(
                    "entries", "context_id" to contextId, "timestamp" to record.getLong("timestamp"),
                    "payload" to payload, "bytes" to size
                )
                count++
                bytes += size * 2
                accepted++
            }
        }
        accepted
    }

    /** SIZE ONLY: a global unsealed-byte threshold starts a pass over every context, including remainders. */
    fun seal(now: Long) {
        val ready = transaction {
            if (scalar("SELECT COALESCE(SUM(bytes),0) FROM entries WHERE batch_id IS NULL") >= batchTargetBytes) {
                db.execSQL("UPDATE control SET drain=1 WHERE id=1")
            }
            scalar("SELECT drain FROM control WHERE id=1") == 1L
        }
        if (!ready) return
        val contexts = mutableListOf<Pair<String, String>>()
        db.rawQuery(
            """SELECT DISTINCT c.id,c.template FROM contexts c JOIN entries e ON c.id=e.context_id
            WHERE e.batch_id IS NULL AND c.dead=0 ORDER BY c.created_at,c.rowid""",
            null
        ).use {
            while (it.moveToNext()) contexts.add(it.getString(0) to it.getString(1))
        }
        contexts.forEach { (id, template) ->
            while (sealOne(id, template, now)) { /* Drain the pass, not just full context batches. */ }
        }
        if (scalar("SELECT COUNT(*) FROM entries WHERE batch_id IS NULL") == 0L) {
            db.execSQL("UPDATE control SET drain=0 WHERE id=1")
        }
    }

    private fun sealOne(contextId: String, template: String, now: Long): Boolean = transaction {
        val records = mutableListOf<Pair<Long, JSONObject>>()
        db.rawQuery(
            "SELECT seq,payload FROM entries WHERE context_id=? AND batch_id IS NULL ORDER BY seq LIMIT ?",
            arrayOf(contextId, MAX_BATCH_ENTRIES.toString())
        ).use {
            while (it.moveToNext()) records.add(it.getLong(0) to JSONObject(it.getString(1)))
        }
        if (records.isEmpty()) return@transaction false
        val id = UUID.randomUUID().toString()
        val selected = mutableListOf<Pair<Long, JSONObject>>()
        var payload: JSONObject? = null
        for (record in records) {
            val candidate = usage(id, template, (selected + record).map { it.second })
            if (TelemetryPayload.wireBytes(candidate) > MAX_BATCH_BYTES) break
            selected.add(record)
            payload = candidate
        }
        if (selected.isEmpty()) {
            // An oversized context+entry can never become deliverable; discard only that entry.
            db.delete("entries", "seq=?", arrayOf(records.first().first.toString()))
            clearEmptyPass()
            return@transaction true
        }
        val serialized = payload!!.toString()
        if (queuedBytes() + serialized.toByteArray(Charsets.UTF_8).size > MAX_PENDING_BYTES) return@transaction false
        insert(
            "batches", "id" to id, "context_id" to contextId, "kind" to "sdk.usage",
            "payload" to serialized, "created_at" to now
        )
        selected.forEach { (seq, _) -> db.execSQL("UPDATE entries SET batch_id=? WHERE seq=?", arrayOf(id, seq)) }
        clearEmptyPass()
        true
    }

    private fun usage(id: String, template: String, records: List<JSONObject>): JSONObject {
        val payload = JSONObject(template)
        payload.put("_id", id).put("eventType", "sdk.usage")
            .put("timestamp", TelemetryPayload.timestamp(records.minOf { it.getLong("timestamp") }))
        payload.getJSONObject(namespace).put("telemetry", JSONObject().put("events", JSONArray(records)))
        return payload
    }

    /** One Core retry owner. Admission is durable; blocked responses undo provisional network accounting. */
    fun beginAttempt(now: Long, orgId: String? = null): Batch? = transaction {
        db.delete("attempts", "started_at<=?", arrayOf((now - 60_000).toString()))
        if (scalar("SELECT COUNT(*) FROM attempts") >= 6 ||
            scalar("SELECT COUNT(*) FROM batches WHERE in_flight=1") != 0L
        ) return@transaction null
        var candidate: Batch? = null
        val orgClause = if (orgId != null) " AND c.org_id=?" else ""
        val args = if (orgId != null) arrayOf(now.toString(), orgId) else arrayOf(now.toString())
        db.rawQuery(
            """SELECT b.id,b.payload,b.attempt FROM batches b JOIN contexts c ON c.id=b.context_id
            WHERE b.in_flight=0 AND b.quarantined=0 AND c.dead=0 AND b.next_at<=?
            AND (b.kind='sdk.adoption' OR c.adopted=1)$orgClause
            ORDER BY b.created_at,b.rowid LIMIT 1""",
            args
        ).use {
            if (it.moveToFirst()) candidate = Batch(it.getString(0), it.getString(1), it.getInt(2) + 1, UUID.randomUUID().toString())
        }
        candidate?.let {
            if (it.attempt > MAX_ATTEMPTS) {
                quarantine(it.id)
                return@transaction null
            }
            db.execSQL(
                "UPDATE batches SET in_flight=1,attempt=?,lease=?,next_at=? WHERE id=?",
                arrayOf(it.attempt, it.lease, now + backoff(it.attempt), it.id)
            )
            insert("attempts", "lease" to it.lease, "started_at" to now)
        }
        candidate
    }

    fun complete(batch: Batch, outcome: String, now: Long, retryAfterMillis: Long? = null) = transaction {
        var context: String? = null
        var kind: String? = null
        db.rawQuery(
            "SELECT context_id,kind FROM batches WHERE id=? AND lease=? AND in_flight=1",
            arrayOf(batch.id, batch.lease)
        ).use {
            if (it.moveToFirst()) { context = it.getString(0); kind = it.getString(1) }
        }
        if (context == null) return@transaction
        when (outcome) {
            "accepted" -> {
                if (kind == "sdk.adoption") db.execSQL("UPDATE contexts SET adopted=1 WHERE id=?", arrayOf(context))
                db.delete("batches", "id=?", arrayOf(batch.id))
            }
            "blocked" -> {
                db.delete("attempts", "lease=?", arrayOf(batch.lease))
                val delay = retryAfterMillis?.coerceIn(1, MAX_RETRY_AFTER_MS) ?: 5_000L
                db.execSQL(
                    "UPDATE batches SET in_flight=0,lease='',attempt=attempt-1,next_at=? WHERE id=?",
                    arrayOf(now + delay, batch.id)
                )
            }
            "drop" -> quarantine(batch.id)
            else -> {
                if (batch.attempt >= MAX_ATTEMPTS) quarantine(batch.id) else {
                    val delay = maxOf(
                        backoff(batch.attempt),
                        retryAfterMillis?.coerceIn(0, MAX_RETRY_AFTER_MS) ?: 0
                    )
                    db.execSQL("UPDATE batches SET in_flight=0,lease='',next_at=? WHERE id=?", arrayOf(now + delay, batch.id))
                }
            }
        }
    }

    private fun backoff(attempt: Int): Long {
        val base = min(3_600_000L, 10_000L * (1L shl min(attempt - 1, 10)))
        return base + Random.nextLong(base / 5 + 1)
    }

    private fun quarantine(id: String) {
        db.execSQL(
            """UPDATE contexts SET dead=1 WHERE id IN
            (SELECT context_id FROM batches WHERE id=? AND kind='sdk.adoption')""",
            arrayOf(id)
        )
        db.execSQL("UPDATE batches SET in_flight=0,lease='',quarantined=1 WHERE id=?", arrayOf(id))
        db.delete("entries", "context_id IN (SELECT id FROM contexts WHERE dead=1)", null)
    }

    fun purgeUnsent() = transaction {
        db.delete("batches", null, null)
        db.delete("entries", null, null)
        db.delete("contexts", "adopted=0", null)
        db.delete("attempts", null, null)
        db.execSQL("UPDATE control SET drain=0 WHERE id=1")
    }

    fun expire(now: Long) = transaction {
        val cutoff = (now - RETENTION_MS).toString()
        db.delete("contexts", "adopted=0 AND created_at<?", arrayOf(cutoff))
        db.delete(
            "batches", "created_at<? OR EXISTS(SELECT 1 FROM entries WHERE batch_id=batches.id AND timestamp<?)",
            arrayOf(cutoff, cutoff)
        )
        db.delete("entries", "timestamp<?", arrayOf(cutoff))
        clearEmptyPass()
        db.execSQL(
            """DELETE FROM contexts WHERE active=0
            AND NOT EXISTS(SELECT 1 FROM entries WHERE context_id=contexts.id)
            AND NOT EXISTS(SELECT 1 FROM batches WHERE context_id=contexts.id)"""
        )
    }

    fun diagnostics(): Map<String, Long> = mapOf(
        "entries" to scalar("SELECT COUNT(*) FROM entries"),
        "batches" to scalar("SELECT COUNT(*) FROM batches"),
        "pendingBytes" to queuedBytes(),
        "networkAttempts" to scalar("SELECT COUNT(*) FROM attempts"),
        "quarantined" to scalar("SELECT COUNT(*) FROM batches WHERE quarantined=1")
    )

    private fun queuedBytes(): Long =
        scalar("SELECT COALESCE(SUM(bytes),0) FROM entries") +
            scalar("SELECT COALESCE(SUM(length(CAST(payload AS BLOB))),0) FROM batches") +
            scalar("SELECT COALESCE(SUM(length(CAST(template AS BLOB))),0) FROM contexts")

    private fun scalar(sql: String): Long = db.rawQuery(sql, null).use { it.moveToFirst(); it.getLong(0) }

    private fun insert(table: String, vararg values: Pair<String, Any>) {
        val content = ContentValues()
        values.forEach { (key, value) ->
            when (value) {
                is String -> content.put(key, value)
                is Int -> content.put(key, value)
                is Long -> content.put(key, value)
                else -> error("Unsupported telemetry column")
            }
        }
        db.insertOrThrow(table, null, content)
    }

    private inline fun <T> transaction(block: () -> T): T {
        db.beginTransaction()
        try {
            val result = block()
            db.setTransactionSuccessful()
            return result
        } finally { db.endTransaction() }
    }

    override fun close() {
        try { db.close() } finally { lock.release(); lockChannel.close() }
    }

    private fun clearEmptyPass() {
        db.execSQL("UPDATE control SET drain=0 WHERE NOT EXISTS(SELECT 1 FROM entries WHERE batch_id IS NULL)")
    }
}
