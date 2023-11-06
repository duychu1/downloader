package com.duycomp.downloader.ui

import android.Manifest
import android.content.ClipboardManager
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.duycomp.downloader.core.designsystem.permissions.RequestPermissions
import com.duycomp.downloader.core.model.DarkThemeConfig
import com.duycomp.downloader.core.model.UserData
import com.duycomp.downloader.navigation.DownloaderNavHost
import com.duycomp.downloader.navigation.ScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DownloaderApp(
    clipboard: ClipboardManager,
    appState: DownloaderAppState = DownloaderAppState(rememberNavController()),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scopeDrawer = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerContent = {
                if (drawerState.isOpen) {
                    TdDrawer(
                        clipboard = clipboard,
                    ) { handleDrawer(scopeDrawer, drawerState) }
                }
            },
            drawerState = drawerState,
            gesturesEnabled = false,
        ) {
            Scaffold(
                bottomBar = {
                    DownloaderBottomBar(
                        destinations = appState.screenDestinations,
                        onNavigateToDestination = appState::navigateToScreenDestination,
                        currentDestination = appState.currentDestination
                    )
                },
            ) { paddingValue ->

                DownloaderNavHost(
                    navController = appState.navController,
                    handleDrawer = { handleDrawer(scopeDrawer, drawerState) },
                    modifier = Modifier.padding(bottom = paddingValue.calculateBottomPadding())
                )

                //write include read
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    RequestPermissions(permissions = listOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                }

            }
        }

    }
}

private fun handleDrawer(
    scopeDrawer: CoroutineScope,
    drawerState: DrawerState
) {
    scopeDrawer.launch {
        drawerState.apply {
            if (isClosed) open() else close()
        }
    }
}

@Composable
private fun DownloaderBottomBar(
    destinations: List<ScreenDestination>,
    onNavigateToDestination: (ScreenDestination) -> Unit,
    currentDestination: NavDestination?,
) {

    Column(Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary.copy(0.1f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier.heightIn(max = 70.dp),
            containerColor = Color.Transparent,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isScreenDestinationInHierarchy(destination)
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = "Navigation Icon"
                        )
                    },
                    label = { Text(destination.title) }
                )
            }
        }
    }
}

private fun NavDestination?.isScreenDestinationInHierarchy(destination: ScreenDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
