package com.ddu.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.util.ToastUtils;

/**
 * Created by yzbzz on 2017/4/27.
 */

public class NotificationUtils {

    public static final String PRIMARY_CHANNEL_ID = "default";
    public static final String PRIMARY_CHANNEL_NAME = "Primary Channel";

    public static final String PRIMARY_CHANNEL_SECOND_ID = "second";
    public static final String PRIMARY_CHANNEL_SECOND_NAME = "Second Channel";

    private Context mContext;
    private NotificationManager mNotificationManager;

    private NotificationUtils() {
        mContext = App.mContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            mNotificationManager.createNotificationChannel(channel);

            NotificationChannel channelSecond = new NotificationChannel(PRIMARY_CHANNEL_SECOND_ID, PRIMARY_CHANNEL_SECOND_NAME, NotificationManager.IMPORTANCE_HIGH);
            channelSecond.setShowBadge(true);
            mNotificationManager.createNotificationChannel(channelSecond);
        }
    }

    public Notification.Builder getNotification(Context context, String ticker, String contentTitle, String contentText, int number, String channelId) {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel(channelId);
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                context.startActivity(intent);
                ToastUtils.showToast("请先打开通知");
            }
            builder = new Notification.Builder(context, PRIMARY_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setTicker(ticker)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setNumber(number)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        return builder;
    }

    public void notify(int id, Notification.Builder builder) {
        mNotificationManager.notify(id, builder.build());
    }

    public static NotificationUtils getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static NotificationUtils instance = new NotificationUtils();
    }

    private static NotificationManager mNManager;
}
