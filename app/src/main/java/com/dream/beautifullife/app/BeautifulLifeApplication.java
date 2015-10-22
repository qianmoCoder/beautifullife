package com.dream.beautifullife.app;

import android.app.Activity;
import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.dream.beautifullife.network.OKHttpManager;
import com.dream.beautifullife.util.AndroidUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/9/24.
 */
public class BeautifulLifeApplication extends Application {

    private Application mApplication;

    private static Activity sCurrentActivity;

    private static Map<Integer, WeakReference<Activity>> sCacheActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        init();

    }

    private void init() {
        String processName = AndroidUtil.getProcessName(mApplication);
        String packageName = mApplication.getPackageName();
        if (processName.equals(packageName)) {// 防止多进程重复实始化
            sCacheActivities = new LinkedHashMap<Integer, WeakReference<Activity>>();
            Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(OKHttpManager.getInstance().getOkHttpClient()));
            initSDK();
        }
    }

    /**
     * 初始化三方SDK
     */
    private void initSDK() {

    }

    /**
     * 向缓存中添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        sCurrentActivity = activity;
        if (sCacheActivities != null) {
            int hashCode = activity.hashCode();
            if (sCacheActivities.containsKey(hashCode)) {
                sCacheActivities.remove(hashCode);
            }
            sCacheActivities.put(hashCode, new WeakReference<Activity>(activity));
        }
    }

    /**
     * 从缓存中移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        int hashCode = activity.hashCode();
        if (sCacheActivities != null && sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode);
        }
    }

    /**
     * 获取缓存中的Activity
     *
     * @param hashCode
     * @return
     */
    public Activity getCacheActivity(int hashCode) {
        WeakReference<Activity> weakReference = sCacheActivities.get(hashCode);
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /**
     * 关闭所有Activity
     *
     * @return
     */
    public static int finishAllActivity() {
        int finishCount = 0;
        if (sCacheActivities != null && !sCacheActivities.isEmpty()) {
            List<WeakReference<Activity>> activities = new ArrayList<WeakReference<Activity>>(sCacheActivities.values());
            for (WeakReference<Activity> activity : activities) {
                Activity tempActivity = activity.get();
                if (tempActivity == null) {
                    continue;
                }
                sCacheActivities.remove(tempActivity.hashCode());
                if (tempActivity != sCurrentActivity) {
                    if (tempActivity != null && !tempActivity.isFinishing()) {
                        tempActivity.finish();
                        finishCount++;
                    }
                }
            }
        }
        return finishCount;
    }
}
