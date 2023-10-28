package com.duycomp.downloader.core.data.di

import com.duycomp.downloader.core.data.UserDataRepository
import com.duycomp.downloader.core.data.UserDataRepositoryImpl
import com.duycomp.downloader.core.data.VideoDatabaseRepository
import com.duycomp.downloader.core.data.VideoDatabaseRepositoryImpl
import com.duycomp.downloader.core.data.VideoNetworkRepository
import com.duycomp.downloader.core.data.VideoNetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
       userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    fun bindsVideoDatabaseRepository(
        videoDataRepository: VideoDatabaseRepositoryImpl
    ): VideoDatabaseRepository

    @Binds
    fun bindsVideoNetworkRepository(
        userDataRepository: VideoNetworkRepositoryImpl
    ): VideoNetworkRepository

}