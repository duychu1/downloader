package com.duycomp.downloader.core.network.di

import com.duycomp.downloader.core.network.DownloaderNetworkDataSource
import com.duycomp.downloader.core.network.DownloaderNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface VideoDataSourceModule {
    @Binds
    fun bindsVideoNetworkDataSource(
       downloaderNetworkDataSource: DownloaderNetworkDataSourceImpl
    ): DownloaderNetworkDataSource
}