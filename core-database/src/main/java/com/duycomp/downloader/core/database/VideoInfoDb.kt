package com.duycomp.downloader.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duycomp.downloader.core.database.VideoInfoDao
import com.duycomp.downloader.core.database.VideoInfoEntity

@Database(entities = [VideoInfoEntity::class], version = 1, )
abstract class VideoInfoDb : RoomDatabase() {
    abstract fun getVideoInfoDao() : VideoInfoDao
}
