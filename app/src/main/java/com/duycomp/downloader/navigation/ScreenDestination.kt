package com.duycomp.downloader.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcon

enum class ScreenDestination(
    val title: String,
    val icon: ImageVector
) {
    DOWNLOAD(
        title = "Download",
        icon = DownloaderIcon.download
    ),
    FILE(
        title = "File",
        icon = DownloaderIcon.folder
    )
}