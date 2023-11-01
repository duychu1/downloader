package com.duycomp.downloader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.MainActivityUiState.Loading
import com.duycomp.downloader.MainActivityUiState.Success
import com.duycomp.downloader.core.common.network.Dispatcher
import com.duycomp.downloader.core.common.network.DownloaderDispatchers
import com.duycomp.downloader.core.data.UserDataRepository
import com.duycomp.downloader.core.model.DarkThemeConfig
import com.duycomp.downloader.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
): ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun setDisableDynamicColor(value: Boolean) {
        viewModelScope.launch {
            userDataRepository.setDynamicColorPreference(value)
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
