plugins {
    id("downloader-android-library")
    id("downloader-android-hilt")
    id("downloader-compose-library")

}

android {
    namespace = "com.duycomp.downloader.feature.file"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

    implementation(project(":core-designsystem"))
    implementation(project(":core-model"))
    implementation(project(":core-common"))
    implementation(project(":core-data"))

    implementation(libs.coil.video)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.android.lifecycle.viewModelCompose)
    implementation(libs.android.lifecycle.runtimeCompose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.media3.exoplayer)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}