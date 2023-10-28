package com.duycomp.downloader.feature.file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.model.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
}

sealed interface VideoDataUiState {
    data object Loading : VideoDataUiState

    data class VideosData(
        val videosData: List<VideoInfo>,
    ) : VideoDataUiState

    data object Empty : VideoDataUiState
}