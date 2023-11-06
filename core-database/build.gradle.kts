plugins {
    id("downloader-android-library")
    id("downloader-android-hilt")
    id("downloader-android-room")
}

android {
    namespace = "com.duycomp.downloader.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

}