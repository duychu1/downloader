package com.duycomp.downloader.core.network

import com.duycomp.downloader.core.network.model.VideoDataNetwork
import com.maxrave.kotlinyoutubeextractor.State
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class DownloaderNetworkDataSourceImpl @Inject constructor(
    private val ytExtractor: YTExtractor
) : DownloaderNetworkDataSource {
    override suspend fun fetchVideoData(url: String): Result<VideoDataNetwork> {
        val id = ytUrlToId(url) ?: return Result.failure(NullPointerException("Can fetch id"))

        ytExtractor.extract(id)

        if (ytExtractor.state == State.SUCCESS) {
            val ytFiles = ytExtractor.getYTFiles()
            val ytFile = ytFiles?.get(22) // 22 for itag hd720 and audio medium quality
            val streamUrl = ytFile?.url

            val videoMeta = ytExtractor.getVideoMeta()
            val title = videoMeta?.title
            val author = videoMeta?.author
            //video length in second
            val videoDuration = videoMeta?.videoLength

            return if (streamUrl != null) Result.success(
                VideoDataNetwork(
                    url = streamUrl.toString(),
                    title = title + author,
                    durationSeconds = videoDuration!!.toLong()
                )
            )
            else Result.failure(NullPointerException("Can not find url"))
        }
        else return Result.failure(NullPointerException("Can not find url"))
    }

    private fun ytUrlToId(url:String): String? {
        val pattern =
            "^(?:(?:https|http):\\/\\/)?(?:www\\.)?(?:youtube\\.com|youtu\\.be).*(?<=\\/|v\\/|u\\/|embed\\/|shorts\\/|watch\\?v=)(?<!\\/user\\/)(?<id>[\\w\\-]{11})(?=\\?|&|\$)"

        val compiledPattern: Pattern = Pattern.compile(
            pattern,
            Pattern.CASE_INSENSITIVE
        )
        val matcher: Matcher = compiledPattern.matcher(url)
        return if (matcher.find()) {
            matcher.group(1).toString()
        } else null
    }
}