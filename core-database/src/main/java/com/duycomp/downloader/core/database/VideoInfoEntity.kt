package com.duycomp.downloader.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val DB_NAME = "videos_data_fb"

@Entity(tableName = DB_NAME)
data class VideoInfoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,

    @ColumnInfo(name = "title") val title:String,

    @ColumnInfo(name = "uri") val uri: String,

    @ColumnInfo(name = "duration") val duration: String,

)


