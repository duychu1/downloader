plugins {
    id("downloader-android-library")
    id("downloader-android-hilt")
    id("downloader-compose-library")

}

android {
    namespace = "com.duycomp.downloader.core.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

    implementation(project(":core-model"))

    implementation(platform(libs.android.compose.bom))

    api(libs.android.compose.foundation)
    api(libs.android.compose.material3)
    api(libs.android.compose.runtime)
    api(libs.android.compose.ui.tooling.preview)
    api(libs.android.compose.ui.tooling)
    api(libs.android.compose.ui.util)
    api(libs.android.compose.foundation.layout)

    implementation(libs.coil.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.android.compose.material.iconsExtended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}