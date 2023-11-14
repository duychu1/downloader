package com.duycomp.downloader.core.data

import android.content.ContentValues.TAG
import android.util.Log
import com.duycomp.downloader.core.data.model.asModel
import com.duycomp.downloader.core.model.VideoInfo
import com.duycomp.downloader.core.network.DownloaderNetworkDataSource
import javax.inject.Inject

class VideoNetworkRepositoryImpl @Inject constructor(
    private val videoNetworkDataSource: DownloaderNetworkDataSource
) : VideoNetworkRepository {
    override suspend fun fetchVideoInfo(url: String): Result<VideoInfo> {
        return (videoNetworkDataSource.fetchVideoData(url).map {
            it.asModel()
        })
    }
}