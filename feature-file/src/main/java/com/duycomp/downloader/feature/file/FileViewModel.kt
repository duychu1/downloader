package com.duycomp.downloader.feature.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.model.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(
    private val videoDatabaseRepository : VideoDatabaseRepository
): ViewModel() {

    val uiSate: StateFlow<VideoDataUiState> =
        videoDatabaseRepository.observeAll().map(
            VideoDataUiState::VideosData
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = VideoDataUiState.Loading,
        )

    fun onDeleteVideo(videoInfo: VideoInfo) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            videoDatabaseRepository.delete(videoInfo)

            Log.d(TAG, "onDeleteVideo: $videoInfo")
            try {
                File(videoInfo.uri).delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    @Throws(Exception::class)
    fun onShareVideo(uri:String, context: Context) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse(uri))
            type = "video/*"
        }
        context.startActivity(Intent.createChooser(shareIntent, null))
    }
}

sealed interface VideoDataUiState {
    data object Loading : VideoDataUiState

    data class VideosData(
        val videosData: List<VideoInfo>,
    ) : VideoDataUiState

    data object Empty : VideoDataUiState
}

private const val TAG = "FileViewModel"