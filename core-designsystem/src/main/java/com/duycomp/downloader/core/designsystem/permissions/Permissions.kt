package com.duycomp.downloader.core.designsystem.permissions

import android.Manifest
import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.*

@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions() {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
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
        when (it.permission) {
            Manifest.permission.READ_EXTERNAL_STORAGE -> {
                when {
                    it.status.isGranted -> {
                        /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                        dlog("read: granted")


                    }
                    it.status.shouldShowRationale -> {
                        /*Happens if a user denies the permission two times
                             */
                        dlog("read: status.shouldShowRationale")

                    }
                    !it.status.isGranted && !it.status.shouldShowRationale -> {
                        /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                        dlog("read: 3")

                    }
                }
            }
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                when {
                    it.status.isGranted -> {
                        /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
                        dlog("write: granted")
                    }
                    it.status.shouldShowRationale -> {
                        /*Happens if a user denies the permission two times
                             */
                        dlog("write: status.shouldShowRationale")

                    }
                    !it.status.isGranted && !it.status.shouldShowRationale -> {
                        /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                        dlog("write: 3")

                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermission() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionState.launchPermissionRequest()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    when {
        permissionState.status.isGranted -> {
            dlog("write: granted")

        }
        permissionState.status.shouldShowRationale -> {
            dlog("write: status.shouldShowRationale")
//            dialogWarnPermission()

        }
        !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
            dlog("write: 3")
            DialogWarnPermission()
        }
    }
}

fun dlog(msg: String) {
    Log.d("dlog", msg)

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