package com.duycomp.downloader.feature.download.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun providesNotificationId(
        @ApplicationContext context: Context,
    ): String {
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        return CHANNEL_ID
    }
}

private const val CHANNEL_ID = "DOWNLOAD_CHANNEL"
private const val CHANNEL_NAME = "Download"
private const val CHANNEL_DESCRIPTION = "This channel is for showing download progress notifications!"