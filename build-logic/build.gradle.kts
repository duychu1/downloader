plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}



dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("downloaderApplicationConventionPlugin") {
            id = "downloader-android-application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("downloaderLibraryConventionPlugin") {
            id = "downloader-android-library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("downloaderHiltConventionPlugin") {
            id = "downloader-android-hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("downloaderComposeApplicationConventionPlugin") {
            id = "downloader-compose-application"
            implementationClass = "AndroidComposeApplicationConventionPlugin"
        }
        register("downloaderComposeLibraryConventionPlugin") {
            id = "downloader-compose-library"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }
        register("downloaderRoomConventionPlugin") {
            id = "downloader-android-room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

    }
}