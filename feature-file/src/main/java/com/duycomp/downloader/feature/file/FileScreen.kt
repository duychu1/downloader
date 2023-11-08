package com.duycomp.downloader.feature.file

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons
import com.duycomp.downloader.core.model.VideoInfo


@Composable
fun FileRoute(
    modifier: Modifier = Modifier,
    viewModel: FileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiSate.collectAsStateWithLifecycle()

    FileScreen(
        uiState = uiState,
        modifier = modifier
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    uiState: VideoDataUiState,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        when (uiState) {
            VideoDataUiState.Loading -> Text("Loading")
            is VideoDataUiState.VideosData ->
                FileScreenContent(
//                    videosData = uiState.videosData
                    videosData = fakeVideoInfo
                )
            is VideoDataUiState.Empty -> Text("No video")
            else -> { }
        }
    }
}

@Composable
fun FileScreenContent(videosData: List<VideoInfo>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            items = videosData,
            key = { item: VideoInfo -> item.id }
        ) {
            CardVideoItem(title = it.title, uri = it.uri, duration = it.duration)
        }
    }
}


@Composable
fun LoadingWheel() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(50.dp)
            .rotate(rotation)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.2f)
                                .background(Color.Green)
                        )
                    }
                }
            }
        }
    }
}
