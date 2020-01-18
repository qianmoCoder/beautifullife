package com.ddu.ui.fragment.work.kotlin.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.ddu.R
import com.ddu.ui.fragment.work.kotlin.notification.util.sendNotification

/**
 * Created by yzbzz on 2020/1/17.
 */
class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
                context.getText(R.string.eggs_ready).toString(),
                context
        )
    }
}