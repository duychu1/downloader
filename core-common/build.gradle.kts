plugins {
    id("downloader-android-library")
    id("downloader-android-hilt")
}

android {
    namespace = "com.duycomp.downloader.core.common"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

}