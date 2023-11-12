package com.duycomp.downloader.core.network.di

import android.content.Context
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YtExtractorModule {

    @Provides
    @Singleton
    fun providesYtExtractor(
        @ApplicationContext context: Context,
    ): YTExtractor = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 2)
}