package com.ddu.ui.fragment.work.kotlin.notification.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.ddu.R
import com.ddu.ui.fragment.work.kotlin.notification.EggTimerActivity
import com.ddu.ui.fragment.work.kotlin.notification.receiver.SnoozeReceiver

/**
 * Created by yzbzz on 2020/1/17.
 */
// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, EggTimerActivity::class.java)

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val eggImage = BitmapFactory.decodeResource(
            applicationContext.resources,
            R.drawable.cooked_egg
    )

    val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(eggImage)
            .bigLargeIcon(null)

    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE,
            snoozeIntent,
            FLAGS)

    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.egg_notification_channel_id))
            .setSmallIcon(R.drawable.cooked_egg)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setLargeIcon(eggImage)
            .addAction(
                    R.drawable.egg_icon,
                    applicationContext.getString(R.string.snooze),
                    snoozePendingIntent
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotification() {
    cancelAll()
}