package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.data.model.VideoInfoNetwork
import javax.inject.Inject

class VideoNetworkRepositoryImpl @Inject constructor() : VideoNetworkRepository {
    override suspend fun fetchVideoInfo(url: String): VideoInfoNetwork {
        return VideoInfoNetwork(
            url = "netUrl",
            authorName = "nerAuthorName",
            aid = "netAid",
            duration = 348,
        )
    }
}