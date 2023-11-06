plugins {
    id("downloader-android-library")
}

android {
    namespace = "com.duycomp.downloader.core.model"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

}