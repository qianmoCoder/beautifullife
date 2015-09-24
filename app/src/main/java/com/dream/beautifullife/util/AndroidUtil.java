package com.dream.beautifullife.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by admin on 2015/9/9.
 */
public class AndroidUtil {

    public static PackageInfo getPackageInfo(Context context){
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
        if (pi == null){
            return "";
        }
        return pi.versionName;
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi == null){
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
}
