package com.duycomp.downloader.navigation

import android.content.ClipboardManager
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.duycomp.downloader.feature.download.navigation.downloadRoute
import com.duycomp.downloader.feature.download.navigation.downloadScreen
import com.duycomp.downloader.feature.file.navigation.fileScreen

@Composable
fun DownloaderNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = downloadRoute,
    modifier: Modifier = Modifier,
    clipboard: ClipboardManager,
    textClipboard: String,
    handleDrawer: () -> Unit = {  },
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
    ) {

        downloadScreen(
            clipboard = clipboard,
            textClipboard = textClipboard,
            handleDrawer
        )

        fileScreen()

    }
}