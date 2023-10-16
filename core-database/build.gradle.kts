plugins {
    id("downloader-android-library")
    id("downloader-hilt")
}

android {
    namespace = "com.duycomp.downloader.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

    implementation(libs.room.runtime)
    implementation(libs.room.compiler)




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}