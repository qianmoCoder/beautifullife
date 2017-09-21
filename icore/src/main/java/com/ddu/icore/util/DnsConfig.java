package com.ddu.icore.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.ddu.icore.app.BaseApp;

/**
 * Created by yzbzz on 2017/9/20.
 */

public class DnsConfig {

    public static ArrayMap<String, String> urlMaps = new ArrayMap<>();

    public static final String BUILD_TYPE = getBuildType();

    static {
        urlMaps.put("qa", "http://qa");
        urlMaps.put("release", "http://release");
        urlMaps.put("debug", "http://debug");
    }

    public static String getBaseUrl() {
        String url = urlMaps.get(BUILD_TYPE);
        if (TextUtils.isEmpty(url)) {
            url = "http://default";
        }
        return url;
    }

    public static String getBuildType() {
        return getMetaData(BaseApp.getContext()).getString("BUILD_TYPE", "debug");
    }


    public static Bundle getMetaData(Context context) {
        Bundle bundle;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            bundle = info.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            bundle = new Bundle();
        }
        return bundle;
    }
}
