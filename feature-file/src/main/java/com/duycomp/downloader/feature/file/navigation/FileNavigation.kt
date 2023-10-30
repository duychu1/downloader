package com.duycomp.downloader.feature.file.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.duycomp.downloader.feature.file.FileRoute

const val fileRoute = "file_route"
fun NavController.navigateToFile(navOptions: NavOptions? = null) {
    this.navigate(fileRoute, navOptions)
}

fun NavGraphBuilder.fileScreen() {
    composable(
        route = fileRoute
    ) {
        FileRoute()
    }
}