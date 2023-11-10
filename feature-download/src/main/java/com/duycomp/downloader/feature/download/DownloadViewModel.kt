package com.duycomp.downloader.feature.download

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.core.common.result.Result
import com.duycomp.downloader.core.data.UserDataRepository
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.data.VideoNetworkRepository
import com.duycomp.downloader.core.model.VideoInfo
import com.duycomp.downloader.feature.download.utils.saveFileToExternal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _textField = MutableStateFlow("")
    val textField: StateFlow<String> = _textField

    val isDownloadBtnOnTop: StateFlow<Boolean> = userDataRepository.userData.map {
        it.isDownloadBtnOnTop
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true,
    )

    private suspend fun saveVideo(
        context: Context,
        url: String
    ) {
            onStatusChange("Collecting video data")

            val videoDataNetwork = videoNetworkRepository.fetchVideoInfo(url)

            onStatusChange("Downloading...")
            toastFromCoroutine(context, "Downloading")

            val saveToLocal = saveFileToExternal(
                url = videoDataNetwork.url,
                mimeType = "video/mp4",
                directory = "Downloader",
                fileName = videoDataNetwork.title,
                context = context
            )

            when(saveToLocal) {
                is Result.Success -> {
                    onStatusChange("Saved: ${videoDataNetwork.title}")
                    videoDatabaseRepository.insert(
                        VideoInfo(
                            id = 0,
                            title = videoDataNetwork.title,
                            uri = saveToLocal.data,
                            duration = videoDataNetwork.toDurationString()
                        )
                    )
                    toastFromCoroutine(context, "Download completed!")
                }
                is Result.Error -> onStatusChange("Cant download, some think went wrong")
                else -> {  }
            }

    }

    suspend fun onTextClipboardChange(textClipboard: String, context: Context) {
        _textField.value = textClipboard
        Log.d(this.javaClass.name, "onTextClipboardChange: --$textClipboard--")
//        saveVideo(context, textClipboard)

    }

    fun onSwapIconClick(isDownloadBtnOnTop: Boolean) = viewModelScope.launch {
        userDataRepository.setBtnDownloadOnTop(isDownloadBtnOnTop)
    }

    fun onPatesClick(clipboard: ClipboardManager) = viewModelScope.launch {
        _textField.value = clipboard.primaryClip?.getItemAt(0)?.text.toString()
    }

    fun onOpenAppClick(context: Context) {
        val intent: Intent? =
            context.packageManager.getLaunchIntentForPackage(OTHER_APP_PACKAGE)

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onDownloadClick(context: Context) = viewModelScope.launch {
        saveVideo(context= context, url = _textField.value)
    }

    fun onTutorialClick() {

    }

    fun onTextFieldChange(value: String)  = viewModelScope.launch {
        _textField.value = value
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

private const val OTHER_APP_PACKAGE = "com.google.android.youtube"