package com.duycomp.downloader.core.data.model

import java.text.DecimalFormat

data class VideoInfoNetwork(
    val url: String,
    val title: String,
    val duration: Long
) {
    fun toDurationString(): String {
        val durationSeconds = duration.toInt() / 1000
        val formatter = DecimalFormat("00")
        val mm = formatter.format(durationSeconds / 60)
        val ss = formatter.format(duration % 60)
        return "$mm:$ss"
    }
}




