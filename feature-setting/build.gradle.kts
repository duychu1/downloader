plugins {
    id("downloader-android-library")
    id("downloader-hilt")
}

android {
    namespace = "com.duycomp.downloader.feature.setting"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

    implementation(project(":core-designsystem"))
    implementation(project(":core-data"))
    implementation(project(":core-model"))

    implementation(libs.hilt.navigation.compose)
    implementation(libs.android.lifecycle.viewModelCompose)
    implementation(libs.android.lifecycle.runtimeCompose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}