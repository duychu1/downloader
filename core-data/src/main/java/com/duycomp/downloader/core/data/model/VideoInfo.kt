package com.duycomp.downloader.core.data.model

import com.duycomp.downloader.core.database.VideoInfoEntity
import com.duycomp.downloader.core.model.VideoInfo

fun VideoInfoEntity.asExternalModel() = VideoInfo (
    id, title, uri, duration
)