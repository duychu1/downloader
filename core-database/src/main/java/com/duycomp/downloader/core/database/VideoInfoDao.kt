package com.duycomp.downloader.core.database

import androidx.room.*
import com.duycomp.downloader.core.database.DB_NAME
import com.duycomp.downloader.core.database.VideoInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoInfoDao {
    @Query("Select * from $DB_NAME order by id desc")
    fun getAllVideosInfo() : Flow<List<VideoInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(videoInfoEntity: VideoInfoEntity)

    @Delete
    suspend fun delete(videoInfoEntity: VideoInfoEntity)

    @Update
    suspend fun update(videoInfoEntity: VideoInfoEntity)
}