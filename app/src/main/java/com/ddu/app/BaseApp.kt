package com.ddu.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.*
import kotlin.properties.Delegates

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        var instance by Delegates.notNull<BaseApp>()

        lateinit var mApp: Application
        lateinit var mContext: Context

        var mainHandler = Handler(Looper.getMainLooper())

        fun post(r: Runnable) {
            mainHandler.post(r)
        }

        fun postDelayed(r: Runnable, delayMillis: Long) {
            mainHandler.postDelayed(r, delayMillis)
        }

        fun getContext(): Application {
            return mApp
        }
    }

    private lateinit var currentActivity: WeakReference<Activity>
    private val sCacheActivities: MutableMap<Int, WeakReference<Activity>> by lazy {
        mutableMapOf<Int, WeakReference<Activity>>()
    }

    val cacheActivities: Map<Int, WeakReference<Activity>>?
        get() = sCacheActivities

    override fun onCreate() {
        super.onCreate()
        mApp = this
        mContext = applicationContext
    }

    open fun addActivity(activity: Activity) {
        currentActivity = WeakReference(activity)
        val hashCode = activity.hashCode()
        if (sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode)
        }
        sCacheActivities.put(hashCode, WeakReference(activity))
    }

    open fun removeActivity(activity: Activity) {
        val hashCode = activity.hashCode()
        if (sCacheActivities.containsKey(hashCode)) {
            sCacheActivities.remove(hashCode)
        }
    }

    open fun getCacheActivity(hashCode: Int): Activity? {
        val weakReference = sCacheActivities[hashCode] ?: return null
        return weakReference.get()
    }

    open fun finishAllActivity(): Int {
        var finishCount = 0
        if (sCacheActivities != null && sCacheActivities.isNotEmpty()) {
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

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

}
