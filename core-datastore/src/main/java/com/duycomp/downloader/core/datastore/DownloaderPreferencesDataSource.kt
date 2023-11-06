package com.duycomp.downloader.core.datastore

import androidx.datastore.core.DataStore
import com.duycomp.downloader.core.model.UserData
import com.duycomp.downloader.core.model.DarkThemeConfig
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DownloaderPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData (
                darkThemeConfig = when (it.darkThemeConfig) {
                    null,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                    DarkThemeConfigProto.UNRECOGNIZED,
                    DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                    ->
                        DarkThemeConfig.FOLLOW_SYSTEM
                    DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                        DarkThemeConfig.LIGHT
                    DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                },
                useDynamicColor = it.useDynamicColor,
                isRate = it.isRate,
                isVip = it.isVip,
                isDownloadBtnOnTop = !it.isDownloadBtnOnBottom
            )
        }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }

    suspend fun setRate(value: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.isRate = value
            }
        }
    }

    suspend fun setVip(value: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.isVip = value
            }
        }
    }

    suspend fun setBtnDownloadOnTop(value: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.isDownloadBtnOnBottom = !value
            }
        }
    }


}