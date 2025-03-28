package com.duycomp.downloader

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureBuildType(commonExtension: CommonExtension<*, *, *, *, *>) {
    commonExtension.apply {
        signingConfigs {

        }
        buildTypes {

            getByName("debug") {
                isMinifyEnabled = false

            }

            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}