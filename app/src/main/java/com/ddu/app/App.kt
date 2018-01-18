package com.ddu.app

import android.app.Activity
import android.content.IntentFilter
import android.content.res.Configuration
import android.content.res.Resources
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.ddu.R
import com.ddu.db.entity.MyObjectBox
import com.ddu.db.entity.StudyContent
import com.ddu.icore.app.BaseApp
import com.ddu.receiver.NetInfoBroadcastReceiver
import com.ddu.util.SystemUtils
import com.ddu.util.xml.PullParserUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.squareup.leakcanary.LeakCanary
import com.umeng.socialize.UMShareAPI
import io.objectbox.Box
import io.objectbox.BoxStore
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.lang.ref.WeakReference
import java.util.*


/**
 * Created by yzbzz on 16/4/6.
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        init()
        UMShareAPI.get(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.addLogAdapter(DiskLogAdapter())
    }

    private fun init() {
        val processName = SystemUtils.getProcessName()
        val currentPackageName = packageName
        if (processName == currentPackageName) {// 防止多进程重复实始化
            setupDatabase()
            registerActivityLifecycleCallbacks(this)
            initData()

            if (!LeakCanary.isInAnalyzerProcess(this)) {
                LeakCanary.install(this)
            }
            registorNetInfoBroadcastReceiver()
        }
    }

    private fun registorNetInfoBroadcastReceiver() {
        val filter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        this.registerReceiver(NetInfoBroadcastReceiver(), filter)
    }

    private fun initData() {

        val studyContentBox = boxStore!!.boxFor(StudyContent::class.java)
        val count = studyContentBox.count()
        if (count <= 0) {
            try {
                loadFile(studyContentBox)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun loadFile(studyContentBox: Box<StudyContent>) {
        try {
            val studyContents = PullParserUtils.getStudyContent(assets.open("config_tag.xml"))
            studyContentBox.put(studyContents)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadStringArray(studyContentBox: Box<StudyContent>) {
        val titleList = resources.getStringArray(R.array.study_ui_title)
        val descList = resources.getStringArray(R.array.study_ui_description)
        val observableTitle = Observable.fromArray(*titleList)
        val observableDescList = Observable.fromArray(*descList)

        Observable.zip(observableTitle, observableDescList, BiFunction<String, String, StudyContent> { s, s2 ->
            val studyItemModel = StudyContent()
            studyItemModel.setTitle(s)
            studyItemModel.setDescription(s2)
            studyItemModel.setType(s)
            studyItemModel
        }).subscribe { studyContent -> studyContentBox.put(studyContent) }
    }


    private fun setupDatabase() {
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }


    /**
     * 向缓存中添加Activity
     *
     * @param activity
     */
    override fun addActivity(activity: Activity) {
        currentActivity = WeakReference(activity)
        if (sCacheActivities != null) {
            val hashCode = activity.hashCode()
            if (sCacheActivities.containsKey(hashCode)) {
                sCacheActivities.remove(hashCode)
            }
            sCacheActivities.put(hashCode, WeakReference(activity))
        }
    }

    /**
     * 从缓存中移除Activity
     *
     * @param activity
     */
    override fun removeActivity(activity: Activity) {
        val hashCode = activity.hashCode()
        if (sCacheActivities != null && sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode)
        }
    }

    /**
     * 获取缓存中的Activity
     *
     * @param hashCode
     * @return
     */
    override fun getCacheActivity(hashCode: Int): Activity? {
        val weakReference = sCacheActivities[hashCode] ?: return null
        return weakReference.get()
    }

    /**
     * 关闭所有Activity
     *
     * @return
     */
    override fun finishAllActivity(): Int {
        var finishCount = 0
        if (sCacheActivities != null && !sCacheActivities.isEmpty()) {
            val activities = ArrayList(sCacheActivities.values)
            for (activity in activities) {
                val tempActivity = activity.get() ?: continue
                sCacheActivities.remove(tempActivity.hashCode())
                if (tempActivity !== currentActivity.get()) {
                    if (tempActivity != null && !tempActivity.isFinishing) {
                        tempActivity.finish()
                        finishCount++
                    }
                }
            }
        }
        return finishCount
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        addActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

    // 强制字体不随着系统改变而改变
    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        var res = super.getResources()
        if (res.configuration.fontScale != 1f) {//非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()
            //设置默认
            //            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            val context = createConfigurationContext(newConfig)
            res = context.resources
            Log.v("lhz", "version: " + Build.VERSION.SDK_INT)
        }
        return res
    }

    companion object {

        var boxStore: BoxStore? = null
            private set
    }
}
