package com.dream.beautifullife.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
}
