package com.ddu.icore;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by yzbzz on 2018/8/21.
 */
public final class ICore {

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

    private ICore() {
    }

    @SuppressWarnings("unchecked")
    public static Context getContext() {
        return getApp().getApplicationContext();
    }

    public static Application getApp() {
        if (null == sApp) {
            throw new RuntimeException("You must call ICore.init first");
        }
        return sApp;
    }

    public static synchronized Handler getMainHandler() {
        if (null == sMainHandler) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
        return sMainHandler;
    }
}
