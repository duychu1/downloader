package com.duycomp.downloader.ui

import android.Manifest
import android.content.ClipboardManager
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.duycomp.downloader.core.designsystem.component.ShareAppDialog
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons
import com.duycomp.downloader.core.designsystem.permissions.RequestPermissions
import com.duycomp.downloader.navigation.DownloaderNavHost
import com.duycomp.downloader.navigation.ScreenDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DownloaderApp(
    clipboard: ClipboardManager,
    textClipboard: String,
    appState: DownloaderAppState = DownloaderAppState(rememberNavController()),
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scopeDrawer = rememberCoroutineScope()
        var showShareDialog by rememberSaveable {
            mutableStateOf(false)
        }

        ModalNavigationDrawer(
            drawerContent = {
                if (drawerState.isOpen) {
                    DownloaderDrawer(
                        clipboard = clipboard,
                    ) { handleDrawer(scopeDrawer, drawerState) }
                }
            },
            drawerState = drawerState,
            gesturesEnabled = true,
        ) {
            if (showShareDialog) {
                ShareAppDialog(
                    clipboard = clipboard,
                    onDismiss = { showShareDialog = false },
                )
            }

            Scaffold(
                bottomBar = {
                    DownloaderBottomBar(
                        destinations = appState.screenDestinations,
                        onNavigateToDestination = appState::navigateToScreenDestination,
                        currentDestination = appState.currentDestination
                    )
                },
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)

                ) {

                    val destination = appState.currentScreenDestination
                    if (destination != null) {
                        DownloaderTopAppBar(
                            title = destination.title,
                            navigationIcon = DownloaderIcons.menu,
                            navigationIconContentDescription = null,
                            actionIcon = DownloaderIcons.share,
                            actionIconContentDescription = null,
                            onActionClick = { showShareDialog = true },
                            onNavigationClick = { handleDrawer(scopeDrawer, drawerState) },
                        )
                    }

                    DownloaderNavHost(
                        navController = appState.navController,
                        clipboard = clipboard,
                        textClipboard = textClipboard,
                        handleDrawer = { handleDrawer(scopeDrawer, drawerState) },
                    )
                }

                //write include read
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    RequestPermissions(
                        permissions = listOf(
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
            color = MaterialTheme.colorScheme.primary.copy(0.3f),
            thickness = 1.dp
        )

        NavigationBar(
//            modifier = Modifier.heightIn(max = 76.dp),
//            containerColor = Color.Transparent,
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
