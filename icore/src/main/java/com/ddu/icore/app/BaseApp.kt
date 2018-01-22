package com.ddu.icore.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.*

open class BaseApp : Application(), Application.ActivityLifecycleCallbacks {

    lateinit var currentActivity: WeakReference<Activity>

    val sCacheActivities: MutableMap<Int, WeakReference<Activity>> by lazy {
        mutableMapOf<Int, WeakReference<Activity>>()
    }

    val cacheActivities: Map<Int, WeakReference<Activity>>?
        get() = sCacheActivities

    override fun onCreate() {
        super.onCreate()
        mApp = this
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

    companion object {

        lateinit var mApp: Application

        private var mainHandler = Handler(Looper.getMainLooper())

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
}
