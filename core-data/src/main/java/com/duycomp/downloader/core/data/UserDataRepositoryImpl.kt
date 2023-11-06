package com.duycomp.downloader.core.data

import com.duycomp.downloader.core.datastore.DownloaderPreferencesDataSource
import com.duycomp.downloader.core.model.DarkThemeConfig
import com.duycomp.downloader.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val downloaderPreferencesDataSource: DownloaderPreferencesDataSource
) : UserDataRepository {
    override val userData: Flow<UserData>
        get() = downloaderPreferencesDataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        downloaderPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        downloaderPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setRate(value: Boolean) {
        downloaderPreferencesDataSource.setRate(value)
    }

    override suspend fun setVip(value: Boolean) {
        downloaderPreferencesDataSource.setVip(value)
    }

    override suspend fun setBtnDownloadOnTop(value: Boolean) {
        downloaderPreferencesDataSource.setBtnDownloadOnTop(value)
    }
}