package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.model.DarkThemeConfig
import com.duycomp.downloader.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
    suspend fun setRate(value: Boolean)
    suspend fun setVip(value: Boolean)
    suspend fun setBtnDownloadOnTop(value: Boolean)

}