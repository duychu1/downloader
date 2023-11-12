package com.duycomp.downloader.ui

import android.Manifest
import android.content.ClipboardManager
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.duycomp.downloader.core.designsystem.component.ShareAppDialog
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons
import com.duycomp.downloader.core.designsystem.permissions.RequestPermissions
import com.duycomp.downloader.navigation.DownloaderNavHost
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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    RequestPermissions(
                        permissions = listOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
//                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                    RequestPermissions(
//                        permissions = listOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE
//                        )
//                    )
//                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    RequestPermissions(
                        permissions = listOf(
                            Manifest.permission.READ_MEDIA_VIDEO,
                            Manifest.permission.READ_MEDIA_IMAGES,

                        )
                    )
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

