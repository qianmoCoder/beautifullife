package com.ddu.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import com.ddu.R;

/**
 * Created by yzbzz on 2017/4/27.
 */

public class NotificationUtils {

    private static NotificationManager mNManager;

    public static void notification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setTicker("通知标题")
                .setContentTitle("这是通知标题")
                .setContentText("这是通知内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .build();
        manager.notify("1", 123, notification);
    }

    public static void sendNotification(Context context, String title, String message, String pushTag, int pushId, Bundle bundle) {
        try {
            if (mNManager == null) {
                mNManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }

            Notification.Builder builder = new Notification.Builder(context)
                    .setTicker(title)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(true);


            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
                builder.setChannelId("1");
                mNManager.createNotificationChannel(channel);
            }

            Notification notification = builder.getNotification();
            mNManager.notify(pushTag, pushId, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
