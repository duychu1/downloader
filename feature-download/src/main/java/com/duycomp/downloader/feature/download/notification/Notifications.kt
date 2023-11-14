package com.duycomp.downloader.feature.download.notification

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun showDownloadNotification(
    context: Context,
    notificationId: Int,
    smallIcon: Int,
    title: String,
    text: String,
    channelId: String,
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) return

    val activityClass = (context as Activity).javaClass
    // Create an explicit intent for an Activity in your app.
    val intent = Intent(context, activityClass)
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(smallIcon)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        // Set the intent that fires when the user taps the notification.
        .setContentIntent(pendingIntent)
        .setProgress(0,0, true)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define.
        Log.d(TAG, "showDownloadNotification: notificationId=$notificationId")
        notify(notificationId, builder.build())
    }
}

fun updateNotification(
    context: Context,
    notificationId: Int,
    title: String,
    text: String,
    channelId: String,
    smallIcon: Int,
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) return

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(smallIcon)
        .setContentTitle(title)
        .setContentText(text)
        .setProgress(0, 0, false)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define.
        Log.d(TAG, "upgradeNotification: notificationId=$notificationId")
        notify(notificationId, builder.build())
    }
}

private const val TAG = "Notifications"
