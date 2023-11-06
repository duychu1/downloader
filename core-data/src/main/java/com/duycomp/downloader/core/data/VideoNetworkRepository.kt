package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.data.model.VideoInfoNetwork

interface VideoNetworkRepository {
    suspend fun fetchVideoInfo(url: String): VideoInfoNetwork
}

