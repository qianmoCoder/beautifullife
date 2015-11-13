package com.beautifullife.core.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by admin on 2015/9/9.
 */
public class AndroidUtil {

    public static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    public static String getAppVersionName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi == null) {
            return "";
        }
        return pi.versionName;
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi == null) {
            return -1;
        }
        return pi.versionCode;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public static String getMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wm.getConnectionInfo().getMacAddress();
        return macAddress;
    }

    public static String getProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * 网络是否已连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 取消或者删除所有状态栏通知
     *
     * @param context
     */
    public static void cancelAllNotification(Context context) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
    }

    /**
     * 调整程序声音类型为媒体播放声音，并且与媒体播放声音大小一致
     *
     * @param context
     */
    public static void adjustVoiceToSystemSame(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, 0);
    }

    /**
     * 隐藏输入法（根据activity当前焦点所在控件的WindowToken）
     */
    public static void hideSoftInput(Activity activity, View editText) {
        View view;
        if (editText == null) {
            view = activity.getCurrentFocus();
        } else {
            view = editText;
        }

        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入法（根据activity当前焦点所在控件的WindowToken）
     */
    public static void showSoftInput(Activity activity, View editText) {
        View view;
        if (editText == null) {
            view = activity.getCurrentFocus();
        } else {
            view = editText;
        }

        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.showSoftInput(view, 0);
        }
    }

    /**
     * Cmwap网络是否已连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnectedByCmwap(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getExtraInfo() != null && networkInfo.getExtraInfo().toLowerCase().contains("cmwap");
    }

    /**
     * 判断是否绑定了手机号
     *
     * @param carrier
     * @return
     */
    public static boolean isBundPhone(String carrier) {
        boolean isBunding = true;
        if (TextUtils.isEmpty(carrier) || carrier.equals("NIL") || carrier.equals("ROBOT")) {
            isBunding = false;
        }
        return isBunding;
    }

    /**
     * 应用是否已经安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            // mContext.getPackageInfo(String packageName, int
            // flags)第二个参数flags为0：因为不需要该程序的其他信息，只需返回程序的基本信息。
            return context.getPackageManager().getPackageInfo(packageName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Wifi网络是否已连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnectedByWifi(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 开启GPU加速
     *
     * @param window
     */
    public static void openGPU(Window window) {
        try {
            // 反射出来硬件加速参数，兼容2.3版本
            Field field = WindowManager.LayoutParams.class.getField("FLAG_HARDWARE_ACCELERATED");
            Field field2 = WindowManager.LayoutParams.class.getField("FLAG_HARDWARE_ACCELERATED");
            if (field != null && field2 != null) {
                window.setFlags(field.getInt(null), field2.getInt(null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据包名判断应用是否在前台运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppOnForeground(Context context, String packageName) {
        if (packageName != null) {
            // Returns a list of application processes that are running on the
            // device
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                    // The name of the process that this object is associated
                    // with.
                    if (appProcess.processName.equals(packageName) && ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isDefaultSms(Context context) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        String packageName = Telephony.Sms.getDefaultSmsPackage(context);
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        String pName = context.getPackageName();
        if (TextUtils.isEmpty(pName)) {
            return false;
        }
        return packageName.equalsIgnoreCase(pName);
    }

    /**
     * 申请成为默认短信
     *
     * @param context
     */
    public static void applyForDefaultSms(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
        context.startActivity(intent);
    }

}
