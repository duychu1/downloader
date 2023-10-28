package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.data.model.VideoInfoNetwork

interface VideoNetworkRepository {
    fun fetchVideoInfo(url: String): VideoInfoNetwork
}

