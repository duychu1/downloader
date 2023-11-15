package com.duycomp.downloader.feature.file

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.duycomp.downloader.core.designsystem.icon.DownloaderIcons

@Composable
fun VideoPlayerDialog(uri: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {

            VideoPlayer(uri = uri)

            IconButton(
                onClick = { onDismiss() },
                modifier = Modifier.align(Alignment.TopStart)
//                        .padding(top = 24.dp)
            ) {
                Icon(
                    imageVector = DownloaderIcons.arrowBack,
                    contentDescription = "close",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@SuppressLint("OpaqueUnitKey")
@Composable
fun VideoPlayer(uri:String) {
    val context = LocalContext.current
    var playWhenReady by remember { mutableStateOf(true) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
            playWhenReady = playWhenReady
            prepare()
            play()
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
//                    FrameLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT
//                    )
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}
