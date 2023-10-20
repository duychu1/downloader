plugins {
    id("downloader-android-library")
    id("downloader-hilt")

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

    implementation(libs.hilt.navigation.compose)
    implementation(libs.android.lifecycle.viewModelCompose)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}