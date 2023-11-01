package com.duycomp.downloader.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.duycomp.downloader.feature.download.navigation.downloadRoute
import com.duycomp.downloader.feature.download.navigation.navigateToDownload
import com.duycomp.downloader.feature.file.navigation.fileRoute
import com.duycomp.downloader.feature.file.navigation.navigateToFile
import com.duycomp.downloader.navigation.ScreenDestination

@Stable
class DownloaderAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentScreenDestination: ScreenDestination?
        @Composable get() = when (currentDestination?.route) {
            downloadRoute -> ScreenDestination.DOWNLOAD
            fileRoute -> ScreenDestination.FILE
            else -> null
        }

    val screenDestinations: List<ScreenDestination> = ScreenDestination.values().asList()

    fun navigateToScreenDestination(screenDestination: ScreenDestination) {
        val screenNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (screenDestination) {
            ScreenDestination.DOWNLOAD -> navController.navigateToDownload(screenNavOptions)
            ScreenDestination.FILE -> navController.navigateToFile(screenNavOptions)
        }
    }
}


