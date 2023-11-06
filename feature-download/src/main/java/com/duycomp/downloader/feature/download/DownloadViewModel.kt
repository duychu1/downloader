package com.duycomp.downloader.feature.download

import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.core.common.result.Result
import com.duycomp.downloader.core.data.UserDataRepository
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.data.VideoNetworkRepository
import com.duycomp.downloader.core.data.model.asEntity
import com.duycomp.downloader.core.model.VideoInfo
import com.duycomp.downloader.feature.download.utils.saveFileToExternal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val videoNetworkRepository: VideoNetworkRepository,
    private val videoDatabaseRepository: VideoDatabaseRepository,
    private val userDataRepository: UserDataRepository,
): ViewModel() {

    private val _status = MutableStateFlow("Let start!")
    val status: StateFlow<String> = _status

    private val _textField = MutableSharedFlow<String>(1)
    val textField: SharedFlow<String> = _textField

    val isDownloadBtnOnTop: StateFlow<Boolean> = userDataRepository.userData.map {
        it.isDownloadBtnOnTop
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true,
    )

    fun saveVideo(
        context: Context,
    ) {
        viewModelScope.launch {
            onStatusChange("Collecting video data")

            val videoDataNetwork = videoNetworkRepository.fetchVideoInfo(textField.toString())

            val fileName = videoDataNetwork.authorName + videoDataNetwork.aid

            onStatusChange("Downloading...")
            toastFromCoroutine(context, "Downloading")

            val saveToLocal = saveFileToExternal(
                url = videoDataNetwork.url,
                mimeType = "video/mp4",
                directory = "Downloader",
                fileName = fileName,
                context = context
            )

            when(saveToLocal) {
                is Result.Success -> {
                    onStatusChange("Saved: $fileName")
                    videoDatabaseRepository.insert(
                        VideoInfo(
                            id = 0,
                            title = fileName,
                            uri = saveToLocal.data,
                            duration = videoDataNetwork.toDurationString()
                        ).asEntity()
                    )
                    toastFromCoroutine(context, "Download completed!")
                }
                is Result.Error -> onStatusChange("Cant download, some think went wrong")
                else -> {  }
            }

        }
    }

    fun onSwapBtnClick(isDownloadBtnOnTop: Boolean) = viewModelScope.launch {
        userDataRepository.setBtnDownloadOnTop(isDownloadBtnOnTop)
    }

    fun onPatesBtnClick(clipboard: ClipboardManager) {

    }

    fun onOpenAppClick() {

    }

    fun onDownloadBtnClick() {

    }


    private fun onStatusChange(value: String) {
        _status.value = value
    }

    private suspend fun toastFromCoroutine(context:Context, msg: String) = withContext(Dispatchers.Main) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }catch (_:Exception) {

        }
    }
}