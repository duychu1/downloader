package com.duycomp.downloader.feature.download.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.duycomp.downloader.feature.download.DownloadRoute


const val downloadRoute = "download_route"
fun NavController.navigateToDownload(navOptions: NavOptions? = null) {
    this.navigate(downloadRoute, navOptions)
}

fun NavGraphBuilder.downloadScreen() {
    composable(
        route = downloadRoute
    ) {
        DownloadRoute()
    }
}