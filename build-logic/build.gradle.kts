plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}



dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("downloaderApplicationConventionPlugin") {
            id = "downloader-android-application"
            implementationClass = "DownloaderApplicationConventionPlugin"
        }
        register("downloaderLibraryConventionPlugin") {
            id = "downloader-android-library"
            implementationClass = "DownloaderLibraryConventionPlugin"
        }
        register("downloaderHiltConventionPlugin") {
            id = "downloader-hilt"
            implementationClass = "DownloaderHiltConventionPlugin"
        }
    }
}