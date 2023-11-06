package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.data.model.VideoInfoNetwork

class VideoNetworkRepositoryImpl : VideoNetworkRepository {
    override suspend fun fetchVideoInfo(url: String): VideoInfoNetwork {
        return VideoInfoNetwork(
            url = "netUrl",
            authorName = "nerAuthorName",
            aid = "netAid",
            duration = 348,
        )
    }
}