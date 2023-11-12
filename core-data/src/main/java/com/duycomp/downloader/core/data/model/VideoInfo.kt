package com.duycomp.downloader.core.data.model

import com.duycomp.downloader.core.database.VideoInfoEntity
import com.duycomp.downloader.core.model.VideoInfo
import com.duycomp.downloader.core.network.model.VideoDataNetwork
import java.text.DecimalFormat

fun VideoInfoEntity.asExternalModel() = VideoInfo (
    id, title, uri, duration
)

fun VideoInfo.asEntity() = VideoInfoEntity (
    title = title,
    uri = uri,
    duration = duration
)

fun toDurationString(durationSeconds: Long): String {
    durationSeconds.toInt()
    val formatter = DecimalFormat("00")
    val mm = formatter.format(durationSeconds / 60)
    val ss = formatter.format(durationSeconds % 60)
    return "$mm:$ss"
}

fun VideoDataNetwork.asModel() = VideoInfo (
    title = title,
    uri = url,
    duration = toDurationString(durationSeconds)
)