package com.duycomp.downloader.core.network

import com.duycomp.downloader.core.network.model.VideoDataNetwork

interface DownloaderNetworkDataSource {

    suspend fun fetchVideoData(url: String): Result<VideoDataNetwork>
}