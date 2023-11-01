package com.duycomp.downloader.core.designsystem.permissions

import android.Manifest
import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions (
    permissions: List<String>, //Manifest.permission.READ_EXTERNAL_STORAGE
) {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = permissions
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
                else -> {

                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    permissionStates.permissions.forEach { it ->

        when {
            it.status.isGranted -> {
                Log.d("RequestPermissions","write: granted")

            }
            it.status.shouldShowRationale -> {
                Log.d("RequestPermissions","write: status.shouldShowRationale")

            }
            !it.status.isGranted && !it.status.shouldShowRationale -> {
                Log.d("RequestPermissions","write: 3")
//                DialogWarnPermission()
            }
        }
    }

}

@Composable
fun DialogWarnPermission() {
    var isShow by remember { mutableStateOf(true) }
    if (isShow) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(text = "Permission") },
            text = { Text(text = "If DENY permission, maybe you CANNOT download video") },
            confirmButton = {
                Button(onClick = { isShow = false }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isShow = false }) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            shape = MaterialTheme.shapes.large
        )
    }
}