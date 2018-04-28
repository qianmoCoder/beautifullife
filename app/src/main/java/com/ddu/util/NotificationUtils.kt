package com.ddu.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import com.ddu.R
import com.ddu.icore.app.BaseApp
import com.ddu.icore.util.ToastUtils
import com.ddu.icore.util.sys.SystemUtils

/**
 * Created by yzbzz on 2017/4/27.
 */

class NotificationUtils private constructor() {

    private val mContext: Context = BaseApp.mContext
    private val mNotificationManager: NotificationManager by lazy {
        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channel)

            val channelSecond = NotificationChannel(PRIMARY_CHANNEL_SECOND_ID, PRIMARY_CHANNEL_SECOND_NAME, NotificationManager.IMPORTANCE_HIGH)
            channelSecond.setShowBadge(true)
            mNotificationManager.createNotificationChannel(channelSecond)
        }
    }

    fun getNotification(context: Context, ticker: String, contentTitle: String, contentText: String, number: Int, channelId: String): Notification.Builder {
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = mNotificationManager.getNotificationChannel(channelId)
            if (channel.importance == NotificationManager.IMPORTANCE_NONE) {
                SystemUtils.openChannelSetting(context, channel.id)
                ToastUtils.showToast("请先打开通知")
            }
            builder = Notification.Builder(context, PRIMARY_CHANNEL_ID)
        } else {
            builder = Notification.Builder(context)
        }
        builder.setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setNumber(number)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
        return builder
    }

    fun notify(id: Int, builder: Notification.Builder) {
        mNotificationManager.notify(id, builder.build())
    }

    private object SingletonHolder {
        val instance = NotificationUtils()
    }

    companion object {

        const val PRIMARY_CHANNEL_ID = "default"
        const val PRIMARY_CHANNEL_NAME = "Primary Channel"

        const val PRIMARY_CHANNEL_SECOND_ID = "second"
        const val PRIMARY_CHANNEL_SECOND_NAME = "Second Channel"

        val instance: NotificationUtils
            get() = SingletonHolder.instance
    }

}
