package com.ddu.icore.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseApp extends Application implements Application.ActivityLifecycleCallbacks {

    @NonNull
    protected static Handler mainThreadHandler = new Handler();

    protected static BaseApp mApplication;

    protected WeakReference<Activity> sCurrentActivity;

    protected Map<Integer, WeakReference<Activity>> sCacheActivities;

    protected static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = getApplicationContext();
    }

    public static BaseApp getApp() {
        return mApplication;
    }

    public static Context getContext() {
        return mContext;
    }

    @NonNull
    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }

    public static void post(Runnable r) {
        mainThreadHandler.post(r);
    }

    public static void postDelayed(Runnable r, long delayMillis) {
        mainThreadHandler.postDelayed(r, delayMillis);
    }

    public Map<Integer, WeakReference<Activity>> getCacheActivities() {
        return sCacheActivities;
    }

    public WeakReference<Activity> getCurrentActivity() {
        return sCurrentActivity;
    }

    public void addActivity(@NonNull Activity activity) {
        sCurrentActivity = new WeakReference<>(activity);
        if (sCacheActivities != null) {
            int hashCode = activity.hashCode();
            if (sCacheActivities.containsKey(hashCode)) {
                sCacheActivities.remove(hashCode);
            }
            sCacheActivities.put(hashCode, new WeakReference<>(activity));
        }
    }

    public void removeActivity(@NonNull Activity activity) {
        int hashCode = activity.hashCode();
        if (sCacheActivities != null && sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode);
        }
    }

    @Nullable
    public Activity getCacheActivity(int hashCode) {
        WeakReference<Activity> weakReference = sCacheActivities.get(hashCode);
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public int finishAllActivity() {
        int finishCount = 0;
        if (sCacheActivities != null && !sCacheActivities.isEmpty()) {
            List<WeakReference<Activity>> activities = new ArrayList<WeakReference<Activity>>(sCacheActivities.values());
            for (WeakReference<Activity> activity : activities) {
                Activity tempActivity = activity.get();
                if (tempActivity == null) {
                    continue;
                }
                sCacheActivities.remove(tempActivity.hashCode());
                if (tempActivity != sCurrentActivity.get()) {
                    if (tempActivity != null && !tempActivity.isFinishing()) {
                        tempActivity.finish();
                        finishCount++;
                    }
                }
            }
        }
        return finishCount;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        removeActivity(activity);
    }
}
