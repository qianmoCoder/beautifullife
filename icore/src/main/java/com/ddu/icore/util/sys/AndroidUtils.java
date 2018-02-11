package com.ddu.icore.util.sys;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import android.widget.EditText;

import java.io.File;
import java.util.List;

/**
 * Created by yzbzz on 16/7/22.
 */
public class AndroidUtils {

    public static String getImsi(@NonNull Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    public static String getDeviceId(@NonNull Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    @Nullable
    public static String getMacAddress(@NonNull Context context) {
        String macAddress = null;
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
        if (null != info) {
            macAddress = info.getMacAddress();

        }
        return macAddress;
    }

    // 设置组件（activity, receiver, service, provider）的启用状态
    public static void setComponentEnabledSetting(@NonNull Context context, Class<?> cls, boolean enabled) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, cls),
                enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    public static boolean isNetworkConnectedByCmwap(@NonNull Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected() && networkInfo.getExtraInfo() != null && networkInfo.getExtraInfo().toLowerCase().contains("cmwap");
    }

    public static boolean isNetworkConnectedByWifi(@NonNull Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }


    @NonNull
    public static String getNetState(@NonNull Context context) {
        String netType = "unknown";
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (isNetworkConnectedByWifi(context)) {
            netType = "WIFI";
        } else if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int mType = info.getSubtype();
            switch (mType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    netType = "GPRS";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    netType = "UMTS";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    netType = "HSDPA";
                    break;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    netType = "EDGE";
                    break;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    netType = "CDMA";
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    netType = "EVDO";
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    netType = "EVDO";
                    break;
            }
        }
        return netType;
    }

    // 2G网络是否已连接
    public static boolean isNetworkConnectedBy2G(@NonNull Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return networkInfo != null
                && null != mTelephony
                && (networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS || networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA || networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE);
    }

    // 是否开启飞行模式
    public static boolean isAirplaneModeOn(@NonNull Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    // sdcard是否可读写
    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isSdcardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long availCount = sf.getAvailableBlocks();
            long blockSize = sf.getBlockSize();
            long availSize = availCount * blockSize / 1024;

            if (availSize >= 3072) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    // 获取文件系统的剩余空间，单位：KB
    public static long getFileSystemAvailableSize(@Nullable File dirName) {
        long availableSize = -1;
        if (dirName != null && dirName.exists()) {
            StatFs sf = new StatFs(dirName.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availableBlocks = sf.getAvailableBlocks();
            availableSize = availableBlocks * blockSize / 1024;
        }
        return availableSize;
    }

    // 复制文本到系统剪切板
    public static void copyToClipboard(@NonNull Context context, String text) {
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setText(text);
    }


    /**
     * 通过string的字符串名获取R.string中对应属性的值
     *
     * @param name 字符串名
     * @return
     */
    public static int getStringResId(@NonNull Context context, String name) {
        return context.getResources().getIdentifier(name, android.R.string.class.getSimpleName(), context.getPackageName());
    }

    /**
     * 通过drawable的字符串名获取R.drawable中对应属性的值
     *
     * @param name 图片名
     * @return
     */
    public static int getDrawableResId(@NonNull Context context, String name) {
        return context.getResources().getIdentifier(name, android.R.drawable.class.getSimpleName(), context.getPackageName());
    }

    /**
     * 在手机主桌面创建应用程序快捷方式（不重复创建），详细内容请看Android系统Launcher或者Launcher2程序源代码
     *
     * @param context Context
     */
    public static void addShortcut(@NonNull Context context, String name, int iconResId, Class<?> classActivity) {
        Intent shortcutIntent = new Intent(context, classActivity);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER); // Intent（注意是否要写老的Intent），如果不添加：Intent.ACTION_MAIN和Intent.CATEGORY_LAUNCHER，桌面快捷方式无法删除
        // Intent unInstallIntent = new
        // Intent("com.android.launcher.action.UNINSTALL_SHORTCUT"); //
        // 删除快捷方式，当应用程序图标或者包名发生变化时使用
        // unInstallIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title); //
        // 包名（注意是否要写老的包名）
        // unInstallIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
        // shortcutIntent);
        // unInstallIntent.putExtra("duplicate", true); //
        // 删除所有的快捷方式，注意：不是所有的Launcher都支持该属性
        // context.sendBroadcast(unInstallIntent);
        boolean shortcutExists = AndroidUtilCompat.shortcutExists(context, name, shortcutIntent);
        if (!shortcutExists) { // 有的Launcher对“duplicate”不起作用
            Intent installIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT"); // 创建快捷方式
            installIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
            installIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResId));
            installIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            installIntent.putExtra("duplicate", false); // 不允许重复添加，注意：不是所有的Launcher都支持该属性
            context.sendBroadcast(installIntent);
        }
    }


    /**
     * 判断Service在系统中是否正在运行
     *
     * @param context
     * @return
     */
    public static boolean isServiceRuning(@NonNull Context context, @Nullable Class<?> serviceClass) {
        if (serviceClass != null) {
            return isServiceRuning(context, serviceClass.getClass().getName());
        }
        return false;
    }

    /**
     * 判断Service在系统中是否正在运行
     *
     * @param context
     * @param serviceClassName Service类名称，包括包名前缀，如：cn.com.fetion.android.services. FetionSMSService
     * @return
     */
    public static boolean isServiceRuning(@NonNull Context context, @Nullable String serviceClassName) {
        if (serviceClassName != null && serviceClassName.length() > 0) {
            ActivityManager ativityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> runningServiceInfos = ativityManager.getRunningServices(-1);
            for (int i = 0; i < runningServiceInfos.size(); i++) {
                if (serviceClassName.equals(runningServiceInfos.get(i).service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断包名对应的应用程序是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppProcessesRuning(@NonNull Context context, @Nullable String packageName) {
        if (packageName != null && packageName.length() > 0) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
            if (runningAppProcessInfo != null) {
                for (ActivityManager.RunningAppProcessInfo appProcInfo : runningAppProcessInfo) {
                    if (appProcInfo != null) {
                        if (packageName.equals(appProcInfo.processName)) {
                            return true;
                        } else if (null != appProcInfo.pkgList) {
                            for (int i = appProcInfo.pkgList.length - 1; i >= 0; i--) {
                                if (packageName.equals(appProcInfo.pkgList[i])) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取Context所在进程的名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(@NonNull Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 设置EditText的光标到最后
     *
     * @param editText
     */
    public static void setSelection(@NonNull EditText editText) {
        if (editText.getText() != null) {
            Editable ea = editText.getText();
            Selection.setSelection(ea, ea.length());
        }
    }

    /**
     * 根据包名获取图标
     */
    public static Drawable getAppIcon(@NonNull Context context, String packageName) {
        PackageManager packManager = context.getPackageManager();
        try {
            return packManager.getApplicationIcon(packManager.getApplicationInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 根据包名获取应用名称
     */
    public static CharSequence getAppTitle(@NonNull Context context, String packageName) {
        PackageManager packManager = context.getPackageManager();
        try {
            return packManager.getApplicationLabel(packManager.getApplicationInfo(packageName, 0));
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static String getCurrentPackageName(@NonNull Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        return component.getPackageName();
    }

    public static void clearWebCache(Context context) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cm = CookieManager.getInstance();
            cm.removeSessionCookie();
            cm.removeAllCookie();
            CookieSyncManager.getInstance().sync();
            WebStorage.getInstance().deleteAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFullscreen(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        } else {
            winParams.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        win.setAttributes(winParams);
    }

    public static void setRotationAnimation(Activity activity, int rotationAnimation) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.rotationAnimation = rotationAnimation;
        win.setAttributes(winParams);
    }

}
