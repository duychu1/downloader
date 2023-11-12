package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.model.VideoInfo

interface VideoNetworkRepository {
    suspend fun fetchVideoInfo(url: String): Result<VideoInfo>
}

