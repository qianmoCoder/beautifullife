package com.ddu.icore;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by yzbzz on 2018/8/21.
 */
public class ICore {

    private static Application sApp;
    private static Handler sMainHandler;

    private static boolean isInit = false;

    public static void init(Application application) {
        if (isInit) {
            return;
        }
        isInit = true;
        sApp = application;
    }

    @SuppressWarnings("unchecked")
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public static Application getApplication() {
        if (null == sApp) {
            throw new RuntimeException("You must call ICore.init first");
        }
        return sApp;
    }

    public static Handler mainHandler() {
        if (null == sMainHandler) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }
}
