package com.ddu.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import com.ddu.app.BaseApp
import com.ddu.icore.common.ext.notificationManager
import com.ddu.icore.util.sys.SystemUtils

/**
 * Created by yzbzz on 2017/4/27.
 */

class NotificationUtils private constructor() {

    private val mNotificationManager: NotificationManager by lazy {
        BaseApp.mContext.notificationManager
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelPrimary = NotificationChannel(PRIMARY_CHANNEL, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channelPrimary.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channelPrimary)

            val channelSecond = NotificationChannel(SECONDARY_CHANNEL, SECONDARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channelSecond.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channelSecond)
        }
    }

    fun getPrimaryNotification(ctx: Context, title: String, body: String): Notification.Builder {
        return getNotification(ctx, title, title, body, 1, PRIMARY_CHANNEL, null, null)
    }

    fun getSecondNotification(ctx: Context, title: String, body: String): Notification.Builder {
        return getNotification(ctx, title, title, body, 1, SECONDARY_CHANNEL, null, null)
    }

    fun getNotification(context: Context, ticker: String, contentTitle: String, contentText: String, number: Int, channelId: String, largeIcon: Bitmap? = null, intent: PendingIntent? = null): Notification.Builder {
        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = mNotificationManager.getNotificationChannel(channelId)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                SystemUtils.openChannelSetting(context, channel.id)
                ToastUtils.showToast("请先打开通知")
            }
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }
        // setDefaults(Notification.DEFAULT_SOUND)
        builder.setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(smallIcon)
                .setWhen(System.currentTimeMillis())
                .setNumber(number)
                .setAutoCancel(true)
        if (largeIcon != null) {
            // BitmapFactory.decodeResource(context.resources, smallIcon)
            builder.setLargeIcon(largeIcon)
        }
        if (intent != null) {
            builder.setContentIntent(intent)
        }
        return builder
    }

    fun notify(id: Int, builder: Notification.Builder) {
        mNotificationManager.notify(id, builder.build())
    }

    private object SingletonHolder {
        val instance = NotificationUtils()
    }

    private val smallIcon: Int
        get() = android.R.drawable.stat_notify_chat

    companion object {

        const val PRIMARY_CHANNEL = "default"
        const val PRIMARY_CHANNEL_NAME = "Primary Channel"

        const val SECONDARY_CHANNEL = "second"
        const val SECONDARY_CHANNEL_NAME = "Second Channel"

        val instance: NotificationUtils
            get() = SingletonHolder.instance
    }

}
