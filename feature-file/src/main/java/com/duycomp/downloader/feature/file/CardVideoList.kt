package com.duycomp.downloader.feature.file

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duycomp.downloader.core.model.VideoInfo

@Composable
fun CardVideoList(items: List<VideoInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),

    ) {
        items(items) {
            CardVideoItem(title = it.title, uri = it.uri, duration = it.duration)
        }
    }

}


val fakeVideoInfo: List<VideoInfo> = listOf(
    VideoInfo(1, "title", "c/iamge", "02:38"),
    VideoInfo(2, "title", "c/iamge", "02:38"),
    VideoInfo(3, "title", "c/iamge", "02:38"),
    VideoInfo(4, "title", "c/iamge", "02:38"),
    VideoInfo(5, "title", "c/iamge", "02:38"),
)


@Preview
@Composable
fun PrevCardVideoList(items: List<VideoInfo> = fakeVideoInfo) {
    CardVideoList(items = items)
    
}