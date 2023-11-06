package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.database.VideoInfoEntity
import com.duycomp.downloader.core.model.VideoInfo
import kotlinx.coroutines.flow.Flow

interface VideoDatabaseRepository {
    fun observeAll(): Flow<List<VideoInfo>>

    suspend fun insert(videoInfoEntity: VideoInfoEntity)

    suspend fun delete(videoInfoEntity: VideoInfoEntity)

    suspend fun update(videoInfoEntity: VideoInfoEntity)

}