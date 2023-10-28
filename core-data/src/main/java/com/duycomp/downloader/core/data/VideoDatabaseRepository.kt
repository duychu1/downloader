package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.model.VideoInfo
import kotlinx.coroutines.flow.Flow

interface VideoDatabaseRepository {
    fun observeAll(): Flow<List<VideoInfo>>
}