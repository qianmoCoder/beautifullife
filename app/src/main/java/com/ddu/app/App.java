package com.ddu.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ddu.R;
import com.ddu.db.entity.StudyContent;
import com.ddu.db.gen.DaoMaster;
import com.ddu.db.gen.DaoSession;
import com.ddu.db.gen.StudyContentDao;
import com.ddu.icore.app.BaseApp;
import com.ddu.util.SystemUtils;
import com.ddu.util.xml.PullParserUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.UMShareAPI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


/**
 * Created by yzbzz on 16/4/6.
 */
public class App extends BaseApp {

    private static DaoSession daoSession;

    private RefWatcher refWatcher;

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
            setupDatabase();
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
        final StudyContentDao studyContentDao = daoSession.getStudyContentDao();
        long count = studyContentDao.count();

        if (count <= 0) {
            try {
                loadFile(studyContentDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadStringArray(final StudyContentDao studyContentDao) {
        String[] titleList = getResources().getStringArray(R.array.study_ui_title);
        String[] descList = getResources().getStringArray(R.array.study_ui_description);
        Observable<String> observableTitle = Observable.fromArray(titleList);
        Observable<String> observableDescList = Observable.fromArray(descList);

        Observable.zip(observableTitle, observableDescList, new BiFunction<String, String, StudyContent>() {
            @Override
            public StudyContent apply(@io.reactivex.annotations.NonNull String s, @io.reactivex.annotations.NonNull String s2) throws Exception {
                StudyContent studyItemModel = new StudyContent();
                studyItemModel.setTitle(s);
                studyItemModel.setDescription(s2);
                studyItemModel.setType(s);
                return studyItemModel;
            }
        }).subscribe(new Consumer<StudyContent>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull StudyContent studyContent) throws Exception {
                studyContentDao.insertOrReplace(studyContent);
            }
        });
    }

    private void loadFile(final StudyContentDao studyContentDao) {
//        Properties props = new Properties();
//        try {
//            props.load(mContext.getAssets().open(""));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            List<StudyContent> studyContents = PullParserUtils.getStudyContent(mContext.getAssets().open("config_tag.xml"));
            studyContentDao.insertOrReplaceInTx(studyContents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ddu", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

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

    // 强制字体不随着系统改变而改变
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            //设置默认
//            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            Context context = createConfigurationContext(newConfig);
            res = context.getResources();
            Log.v("lhz","version: " + Build.VERSION.SDK_INT);
        }
        return res;
    }
}
