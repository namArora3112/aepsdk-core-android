plugins {
    id("aep-library")
}

val coreExtensionVersion: String by project

aepLibrary {
    namespace = "com.adobe.marketing.mobile.telemetry"
    moduleName = "sdktelemetry"
    moduleVersion = "0.1.0-validation"
    enableDokkaDoc = false
    enableSpotless = true
    enableCheckStyle = true
    publishing {
        mavenRepoName = "AdobeMobileTelemetry"
        mavenRepoDescription = "Optional Mobile SDK telemetry validation plugin"
        gitRepoName = "aepsdk-core-android"
        addCoreDependency(coreExtensionVersion)
    }
}

dependencies {
    implementation(project(":core"))
    testImplementation("org.robolectric:robolectric:4.7")
}
