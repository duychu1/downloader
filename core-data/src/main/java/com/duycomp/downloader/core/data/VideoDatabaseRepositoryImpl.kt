package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.data.model.asExternalModel
import com.duycomp.downloader.core.database.VideoInfoDao
import com.duycomp.downloader.core.database.VideoInfoEntity
import com.duycomp.downloader.core.model.VideoInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VideoDatabaseRepositoryImpl @Inject constructor(
    private val videoInfoDao: VideoInfoDao
) : VideoDatabaseRepository {
    override fun observeAll(): Flow<List<VideoInfo>> =
        videoInfoDao.getVideosInfo().map {
            it.map(VideoInfoEntity::asExternalModel)
        }

    override suspend fun insert(videoInfoEntity: VideoInfoEntity) {
        videoInfoDao.insert(videoInfoEntity)
    }

    override suspend fun delete(videoInfoEntity: VideoInfoEntity) {
        videoInfoDao.delete(videoInfoEntity)
    }

    override suspend fun update(videoInfoEntity: VideoInfoEntity) {
        videoInfoDao.update(videoInfoEntity)
    }

}