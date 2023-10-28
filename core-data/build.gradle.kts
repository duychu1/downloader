plugins {
    id("downloader-android-library")
    id("downloader-hilt")
}

android {
    namespace = "com.duycomp.downloader.core.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {

    implementation(project(":core-model"))
    implementation(project(":core-common"))
    implementation(project(":core-database"))
    implementation(project(":core-datastore"))


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}