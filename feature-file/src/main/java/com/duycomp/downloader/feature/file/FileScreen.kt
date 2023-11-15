package com.duycomp.downloader.feature.file

import android.content.Context
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duycomp.downloader.core.model.VideoInfo


@Composable
fun FileRoute(
    modifier: Modifier = Modifier,
    viewModel: FileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiSate.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var isShowPlayVideo by remember { mutableStateOf(false) }

    var uri by remember { mutableStateOf("") }

    val onImageClick: (String) -> Unit = {
        isShowPlayVideo = true
        uri = it
    }

    if (isShowPlayVideo) VideoPlayerDialog(uri = uri, onDismiss = { isShowPlayVideo = false })

    FileScreen(
        uiState = uiState,
        modifier = modifier,
        onDeleteVideo = viewModel::onDeleteVideo,
        onShareVideo = viewModel::onShareVideo,
        onImageClick = onImageClick
    )

}


@Composable
fun FileScreen(
    uiState: VideoDataUiState,
    modifier: Modifier,
    onImageClick: (String) -> Unit,
    onDeleteVideo: (VideoInfo) -> Unit,
    onShareVideo: (String, Context) -> Unit
) {
    Column(modifier = modifier) {
        when (uiState) {
            VideoDataUiState.Loading -> Text("Loading")
            is VideoDataUiState.VideosData ->
                FileScreenContent(
                    videosData = uiState.videosData,
                    onImageClick = onImageClick,
                    onDeleteVideo = onDeleteVideo,
                    onShareVideo = onShareVideo
                )

            is VideoDataUiState.Empty -> Text("No video")
        }
    }
}

@Composable
fun FileScreenContent(
    videosData: List<VideoInfo>,
    onImageClick: (String) -> Unit,
    onDeleteVideo: (VideoInfo) -> Unit,
    onShareVideo: (String, Context) -> Unit
) {

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
            val context = LocalContext.current
            CardVideoItem(
                title = it.title,
                uri = it.uri,
                duration = it.duration,
                onDeleteIconClick = { onDeleteVideo(it) },
                onImageClick = { onImageClick(it.uri) },
                onShareIconClick = { onShareVideo(it.uri, context) },
            )
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
