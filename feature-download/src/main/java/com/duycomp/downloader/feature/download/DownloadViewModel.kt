package com.duycomp.downloader.feature.download

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duycomp.downloader.core.common.result.Result
import com.duycomp.downloader.core.common.result.asResult
import com.duycomp.downloader.core.data.UserDataRepository
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.data.VideoNetworkRepository
import com.duycomp.downloader.feature.download.notification.showDownloadNotification
import com.duycomp.downloader.feature.download.notification.updateNotification
import com.duycomp.downloader.feature.download.utils.saveFileToExternal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val channelId: String,
) : ViewModel() {

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

    private var nId = 1
    private val mapDownloadingId: MutableMap<String, Int> = mutableStateMapOf()


    private suspend fun saveVideo(
        context: Context,
        url: String
    ) = withContext(Dispatchers.IO) {
        nId++
        onStatusChange("Collecting data")

        val videoInfo = videoNetworkRepository.fetchVideoInfo(url).fold(
            onSuccess = { it },
            onFailure = {
                onStatusChange("Can not fetch data!")
                return@withContext
            }
        )

        if (mapDownloadingId.contains(videoInfo.uri)) return@withContext

        Log.d(TAG, "saveVideo: thread:${Thread.currentThread().name}")

        mapDownloadingId[url] = nId

        saveFileToExternal(
            url = videoInfo.uri,
            mimeType = "video/mp4",
            directory = "Downloader",
            fileName = videoInfo.title,
            context = context
        )
            .asResult()
//            .flowOn(Dispatchers.IO)
            .collect { saveToExternal ->
                Log.d(TAG, "saveVideo: collect")
                when (saveToExternal) {
                    is Result.Loading -> {
                        withContext(Dispatchers.Default) {
                            if(mapDownloadingId.size == 1)
                                onStatusChange("Downloading...")
                            else if(mapDownloadingId.size > 1)
                                onStatusChange("Downloading ${mapDownloadingId.size} video...")
                        }
                        toastFromCoroutine(context, "Downloading...")

                        showDownloadNotification(
                            context = context,
                            notificationId = mapDownloadingId[url]!!,
                            smallIcon = R.drawable.downloading,
                            title = videoInfo.title,
                            text = "Downloading...",
                            channelId = channelId,
                        )

                    }

                    is Result.Success -> {

                        onStatusChange("Saved: ${videoInfo.title.take(18)}...")

                        withContext(Dispatchers.IO) {
                            videoDatabaseRepository.insert(
                                videoInfo.copy(
                                    uri = saveToExternal.data
                                )
                            )
                        }

                        toastFromCoroutine(context, "Download completed!")

                        updateNotification(
                            context = context,
                            notificationId = mapDownloadingId[url]!!,
                            text = videoInfo.title,
                            title = "Download completed!",
                            smallIcon = R.drawable.download_done,
                            channelId = channelId,
                        )

                        mapDownloadingId.remove(url)
                        Log.d(TAG, "saveVideo: mapDownloadingId=${mapDownloadingId.size}")
                        Log.d(TAG, "saveVideo: mapDownloadingId=$mapDownloadingId")
                        
                        delay(3000)
                        if(mapDownloadingId.size == 1)
                            onStatusChange("Downloading...")
                        else if(mapDownloadingId.size > 1)
                            onStatusChange("Downloading ${mapDownloadingId.size} video...")
                    }

                    is Result.Error -> {
                        onStatusChange("Download error!")
                        println(saveToExternal.exception?.printStackTrace())

                    }
                }
            }

        //download manager
//        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val request = DownloadManager.Request(Uri.parse(videoInfo.uri))
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
//        request.setTitle(videoInfo.title)
//        request.setDescription("Downloading...")
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Downloader/${videoInfo.title}.mp4")
//        downloadManager.enqueue(request)
    }

    suspend fun onTextClipboardChange(textClipboard: String, context: Context) =
        viewModelScope.launch {
            Log.d(TAG, "onTextClipboardChange: textClipboard=$textClipboard")
            Log.d(TAG, "onTextClipboardChange: _textField=${_textField.value}")
            if (_textField.value == textClipboard) return@launch
            _textField.value = textClipboard
            if (!checkMatchUrl(textClipboard) && textClipboard != "") {
                onStatusChange("Your url not correct")
                return@launch
            }
//        Log.d(TAG, "onTextClipboardChange: value = $textClipboard")
            saveVideo(context, textClipboard)

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
        saveVideo(context = context, url = _textField.value)
    }

    fun onTutorialClick() {

    }

    fun onTextFieldChange(value: String) = viewModelScope.launch {
        _textField.value = value
    }


    private fun onStatusChange(value: String) {
        _status.value = value
    }

    private fun checkMatchUrl(url: String): Boolean {
        return url.contains("youtu")
    }

    private suspend fun toastFromCoroutine(context: Context, msg: String) =
        withContext(Dispatchers.Main) {
            try {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {

            }
        }

}

private const val OTHER_APP_PACKAGE = "com.google.android.youtube"

private const val TAG = "DownloadViewModel"

