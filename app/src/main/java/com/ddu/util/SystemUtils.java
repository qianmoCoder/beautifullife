package com.ddu.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ddu.app.App;

import java.util.List;

/**
 * Created by lhz on 16/3/3.
 */
public class SystemUtils {

    private static int VERSION_CODE;
    private static String VERSION_NAME;

    // 返回
    public static void onTitleBackPressed(@NonNull Activity activity) {
        hideSoftInput(activity, null);
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        activity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
    }

    /**
     * 隐藏输入法（根据activity当前焦点所在控件的WindowToken）
     */
    public static void hideSoftInput(@NonNull Activity activity, @Nullable View editText) {
        View view;
        if (editText == null) {
            view = activity.getCurrentFocus();

        } else {
            view = editText;
        }

        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入法（根据activity当前焦点所在控件的WindowToken）
     */
    public static void showSoftInput(@NonNull Activity activity, @Nullable View editText) {
        View view;
        if (editText == null) {
            view = activity.getCurrentFocus();
        } else {
            view = editText;
        }

        if (view != null) {
            InputMethodManager inputMethod = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethod.showSoftInput(view, 0);
        }
    }

    public static String getDeviceId() {
        Context context = App.getContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public static String getMacAddress() {
        Context context = App.getContext();
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wm.getConnectionInfo().getMacAddress();
        return macAddress;
    }

    public static String getProcessName() {
        int pid = android.os.Process.myPid();
        Context context = App.getContext();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    public static int getVersionCode() {
        try {
            if (VERSION_CODE < 0) {
                Context context = App.getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                VERSION_CODE = info.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            VERSION_CODE = -1;
        }
        return VERSION_CODE;
    }

    public static String getVersionName() {
        try {
            if (TextUtils.isEmpty(VERSION_NAME)) {
                Context context = App.getContext();
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                VERSION_NAME = info.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            VERSION_NAME = "";
        }
        return VERSION_NAME;
    }
}
