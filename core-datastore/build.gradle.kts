plugins {
    id("downloader-android-library")
    id("downloader-hilt")
    alias(libs.plugins.protobuf)

}

android {
    namespace = "com.duycomp.downloader.core.datastore"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

//androidComponents.beforeVariants {
//    android.sourceSets.register(it.name) {
//        val buildDir = layout.buildDirectory.get().asFile
//        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
//        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
//    }
//}

dependencies {

    implementation(libs.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
    implementation(project(":core-common"))
    implementation(project(":core-model"))



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}