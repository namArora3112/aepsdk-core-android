#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 4 ]]; then
    echo "Usage: bash $0 CORE_CHECKOUT TELEMETRY_CHECKOUT MESSAGING_CHECKOUT PRIVATE_MAVEN_DIR" >&2
    echo "Use poc/telemetry-hybrid-migration in all three repositories." >&2
    echo "Set JAVA_HOME (JDK 17), ANDROID_HOME and ANDROID_SERIAL (connected emulator)." >&2
    exit 2
fi
: "${JAVA_HOME:?Set JAVA_HOME to JDK 17}"
: "${ANDROID_HOME:?Set ANDROID_HOME}"
: "${ANDROID_SERIAL:?Choose an existing emulator explicitly}"

core="$(cd "$1" && pwd)"
telemetry="$(cd "$2" && pwd)"
messaging="$(cd "$3" && pwd)"
mkdir -p "$4"
export POC_MAVEN_REPO
POC_MAVEN_REPO="$(cd "$4" && pwd)"
init="$(cd "$(dirname "$0")" && pwd)/validation.init.gradle"

for checkout in "$core" "$telemetry" "$messaging"; do
    if [[ "$(git -C "$checkout" branch --show-current)" != "poc/telemetry-hybrid-migration" ]]; then
        echo "Wrong branch in $checkout; use poc/telemetry-hybrid-migration." >&2
        exit 2
    fi
done

(cd "$core/code" && ./gradlew -I "$init" \
    :core:spotlessCheck :core:checkstyle :core:testPhoneDebugUnitTest \
    --tests '*HybridTelemetryTests' --tests '*EventHubTelemetryTests' \
    :core:publishReleasePublicationToHybridPocRepository --max-workers=2 --console=plain)
(cd "$telemetry/code" && ./gradlew -I "$init" \
    :telemetry:testDebugUnitTest :telemetry:publishMavenPublicationToHybridPocRepository \
    --max-workers=2 --console=plain)
(cd "$messaging/code" && ./gradlew -I "$init" \
    :messaging:spotlessCheck :messaging:checkstyle :messaging:testPhoneDebugUnitTest \
    --tests '*MessagingTests' --tests '*IamRefreshHandlerTests' \
    --tests '*EdgePersonalizationResponseHandlerTests' \
    :messaging:connectedPhoneDebugAndroidTest \
    -Pandroid.testInstrumentationRunnerArguments.class=com.adobe.marketing.mobile.messaging.RefreshEventCorrelationPocTests,com.adobe.marketing.mobile.messaging.HubSignatureProjectionPocTests,com.adobe.marketing.mobile.messaging.HybridTelemetryMigrationPocTests \
    --max-workers=2 --console=plain)

echo "POC checks passed. This does not certify production, older Core, iOS or wrappers."
