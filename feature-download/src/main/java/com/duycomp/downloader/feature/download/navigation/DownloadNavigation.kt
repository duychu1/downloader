package com.duycomp.downloader.feature.download.navigation

import android.content.ClipboardManager
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.duycomp.downloader.feature.download.DownloadRoute
import kotlinx.coroutines.handleCoroutineException


const val downloadRoute = "download_route"
fun NavController.navigateToDownload(navOptions: NavOptions? = null) {
    this.navigate(downloadRoute, navOptions)
}

fun NavGraphBuilder.downloadScreen(
    clipboard: ClipboardManager,
    textClipboard: String,
    handlerDrawer: () -> Unit = {},
) {
    composable(
        route = downloadRoute
    ) {
        DownloadRoute(
            clipboard = clipboard,
            textClipboard = textClipboard,
            handleDrawer = handlerDrawer
        )
    }
}