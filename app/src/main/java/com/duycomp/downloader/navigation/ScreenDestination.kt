package com.duycomp.downloader.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons

enum class ScreenDestination(
    val title: String,
    val icon: ImageVector
) {
    DOWNLOAD(
        title = "Download",
        icon = DownloaderIcons.download
    ),
    FILE(
        title = "File",
        icon = DownloaderIcons.folder
    )
}