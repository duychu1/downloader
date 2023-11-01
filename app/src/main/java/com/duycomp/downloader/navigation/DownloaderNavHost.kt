package com.duycomp.downloader.navigation

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
    modifier: Modifier,
    handleDrawer: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        downloadScreen()

        fileScreen()

    }
}