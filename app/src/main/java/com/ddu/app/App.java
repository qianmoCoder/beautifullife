package com.ddu.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ddu.R;
import com.ddu.icore.app.BaseApp;
import com.ddu.util.SystemUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by yzbzz on 16/4/6.
 */
public class App extends BaseApp {

//    private static DaoSession daoSession;

    private RefWatcher refWatcher;

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setQQZone("1101352191","UB0rBnD2D1d4CGod");
        PlatformConfig.setSinaWeibo("2623948793","bcba1c5f2947cf26b5be2536b05151d0","");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mContext = getApplicationContext();
        init();
        UMShareAPI.get(this);
    }

    private void init() {
        String processName = SystemUtils.getProcessName();
        String packageName = mApplication.getPackageName();
        if (processName.equals(packageName)) {// 防止多进程重复实始化
            sCacheActivities = new LinkedHashMap<>();
//            setupDatabase();
            registerActivityLifecycleCallbacks(this);
            initData();
            refWatcher = LeakCanary.install(this);
        }
    }

    public static RefWatcher getRefWatcher(@NonNull Context context) {
        App app = (App) context.getApplicationContext();
        return app.refWatcher;
    }

    private void initData() {
        String[] titleList = getResources().getStringArray(R.array.study_ui_title);
        String[] descList = getResources().getStringArray(R.array.study_ui_description);

//        final StudyContentDao studyContentDao = daoSession.getStudyContentDao();
//        studyContentDao.deleteAll();
//
//        Observable<String> observableTitle = Observable.from(titleList);
//        Observable<String> observableDescList = Observable.from(descList);
//
//        Observable.zip(observableTitle, observableDescList, new Func2<String, String, StudyContent>() {
//            @NonNull
//            @Override
//            public StudyContent call(String title, String desc) {
//                StudyContent studyItemModel = new StudyContent();
//                studyItemModel.setTitle(title);
//                studyItemModel.setDescription(desc);
//                studyItemModel.setType(title);
//                return studyItemModel;
//            }
//        }).subscribe(new Subscriber<StudyContent>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(StudyContent studyContent) {
//                studyContentDao.insertOrReplace(studyContent);
//            }
//        });
    }

//    private void setupDatabase() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ddu", null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(db);
//        daoSession = daoMaster.newSession();
//    }
//
//    public static DaoSession getDaoSession() {
//        return daoSession;
//    }

    /**
     * 向缓存中添加Activity
     *
     * @param activity
     */
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

    /**
     * 从缓存中移除Activity
     *
     * @param activity
     */
    public void removeActivity(@NonNull Activity activity) {
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
    @Nullable
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
