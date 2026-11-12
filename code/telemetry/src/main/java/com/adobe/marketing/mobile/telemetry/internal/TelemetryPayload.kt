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

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.adobe.marketing.mobile.internal.telemetry.ApiCall
import com.adobe.marketing.mobile.internal.telemetry.SdkTelemetry
import com.adobe.marketing.mobile.internal.telemetry.TelemetrySnapshot
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/** Fixed SDK metadata only. The caller-supplied version never overrides the saved inventory. */
internal object TelemetryPayload {
    private val extension = Regex("com\\.adobe\\.[a-zA-Z0-9_.]{1,120}")
    private val api = Regex("[A-Za-z][A-Za-z0-9_]*(\\.[A-Za-z][A-Za-z0-9_]*)*")
    private val version = Regex("[A-Za-z0-9._+\\-]{1,64}")
    private val failureTypes = setOf("decisioning.propositionFetch", "personalization.request")
    private val failureCodes = setOf("EXEG-0003-400", "https://ns.adobe.com/aep/errors/EXEG-0003-400")

    fun allowed(call: ApiCall): Boolean =
        call.apiName.length in 1..192 && extension.matches(call.extensionName) &&
            api.matches(call.apiName) && version.matches(call.extensionVersion)

    fun record(call: ApiCall): JSONObject? =
        if (!allowed(call)) null else entry(
            "API_CALL", call.extensionName,
            if (call.extensionName == "com.adobe.messaging") call.apiName.removePrefix("Messaging.") else call.apiName,
            call.timestampMillis
        )

    fun timeline(name: String, now: Long): JSONObject {
        require(name == "appStart" || name == "adoptionUpgrade")
        return entry("TIMELINE", SdkTelemetry.CORE, name, now)
    }

    fun failureTypeAllowed(type: String): Boolean = type in failureTypes

    /** Attribution is to the correlated Edge event type, not to an inferred public API call. */
    fun failure(type: String, code: String, name: String, now: Long): JSONObject? =
        if (type in failureTypes && code in failureCodes && name == "Invalid datastream ID") {
            entry("API_FAIL", "com.adobe.edge", type, now).put("errorCode", code).put("errorName", name)
        } else null

    private fun entry(type: String, extension: String, name: String, now: Long): JSONObject =
        JSONObject().put("apiEventType", type).put("timestamp", now)
            .put("extension", extension).put("apiName", name)

    fun timestamp(value: Long): String =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(Date(value))

    fun versionKey(appVersion: String, versions: Map<String, String>): String {
        val input = JSONArray().put(appVersion)
        versions.toSortedMap().forEach { (name, version) -> input.put(JSONArray().put(name).put(version)) }
        return MessageDigest.getInstance("SHA-256").digest(input.toString().toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }

    @Suppress("DEPRECATION")
    fun packageInfo(context: Context): PackageInfo =
        context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)

    fun adoption(context: Context, namespace: String, installId: String, snapshot: TelemetrySnapshot, now: Long): JSONObject {
        val info = packageInfo(context)
        val permissions = JSONArray()
        info.requestedPermissions?.forEachIndexed { index, name ->
            val granted = (
                (info.requestedPermissionsFlags?.getOrNull(index) ?: 0) and
                    PackageInfo.REQUESTED_PERMISSION_GRANTED
                ) != 0
            permissions.put(JSONObject().put("name", name).put("granted", granted.toString()))
        }
        val versions = JSONArray()
        snapshot.versions.toSortedMap().forEach { (name, version) ->
            versions.put(JSONObject().put("name", name).put("version", version))
        }
        val metadata = JSONObject().put("uuid", installId).put("platform", "android")
            .put("osVersion", Build.VERSION.RELEASE)
            .put(
                "application",
                JSONObject().put("package", context.packageName)
                    .put("name", context.packageManager.getApplicationLabel(context.applicationInfo).toString())
                    .put("version", info.versionName ?: "")
            )
            .put("device", JSONObject().put("manufacturer", Build.MANUFACTURER).put("model", Build.MODEL))
            .put("permissions", permissions)
        return JSONObject().put("eventType", "sdk.adoption").put("timestamp", timestamp(now))
            .put(namespace, JSONObject().put("orgId", snapshot.orgId).put("metadata", metadata).put("sdkVersions", versions))
    }

    /** Exact Edge wire wrapper used by the adapter, including empty identityMap and events. */
    fun wireBytes(payload: JSONObject): Int =
        JSONObject().put("xdm", JSONObject().put("identityMap", JSONObject()))
            .put("events", JSONArray().put(JSONObject().put("xdm", payload)))
            .toString().toByteArray(Charsets.UTF_8).size
}
