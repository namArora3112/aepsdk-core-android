#!/usr/bin/env bash
set -euo pipefail

CORE_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
: "${TELEMETRY_DIR:?Set TELEMETRY_DIR to the telemetry catalog worktree}"
: "${MESSAGING_DIR:?Set MESSAGING_DIR to the Messaging catalog worktree}"
: "${POC_MAVEN_REPO:?Set POC_MAVEN_REPO to a private absolute Maven directory}"
: "${JAVA_HOME:?Set JAVA_HOME to JDK 17}"
: "${ANDROID_HOME:?Set ANDROID_HOME to the Android SDK}"
export POC_MAVEN_REPO JAVA_HOME ANDROID_HOME
INIT="$CORE_DIR/scripts/event-catalog-poc/validation.init.gradle"

(
    cd "$CORE_DIR/code"
    ./gradlew -I "$INIT" :core:spotlessCheck :core:checkstyle \
        :core:testPhoneDebugUnitTest --tests '*CatalogSelectorTests' \
        --tests '*HybridTelemetryTests' --tests '*EventHubTelemetryTests' \
        :core:publishReleasePublicationToCatalogPocRepository --max-workers=2 --console=plain
)
(
    cd "$TELEMETRY_DIR/code"
    ./gradlew -I "$INIT" :telemetry:testDebugUnitTest :telemetry:lintDebug \
        :telemetry:publishMavenPublicationToCatalogPocRepository --max-workers=2 --console=plain
)
(
    cd "$MESSAGING_DIR/code"
    tasks=(:testapp:assembleDebugAndroidTest)
    if [[ -n "${ANDROID_SERIAL:-}" ]]; then
        export ANDROID_SERIAL
        tasks+=(:testapp:connectedDebugAndroidTest)
    fi
    ./gradlew -I "$INIT" -PtelemetryCatalogPoc=true \
        "-PtelemetryPocOrg=${TELEMETRY_POC_ORG:-}" "-PtelemetryPocEndpoint=${TELEMETRY_POC_ENDPOINT:-}" \
        :messaging:testPhoneDebugUnitTest \
        --tests '*MessagingTests' --tests '*IamRefreshHandlerTests' \
        --tests '*EdgePersonalizationResponseHandlerTests' \
        :testapp:assembleDebug :testapp:lintDebug "${tasks[@]}" \
        -Pandroid.injected.androidTest.leaveApksInstalledAfterRun=true --max-workers=2 --console=plain
)
