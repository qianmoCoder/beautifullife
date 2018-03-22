package com.ddu.icore.common

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.annotation.RequiresPermission
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.content.edit
import org.jetbrains.anko.connectivityManager
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.powerManager

/**
 * Created by yzbzz on 2018/1/18.
 */

val Context.mateData
    get() = applicationInfo.metaData

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.versionName
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode
    get() = packageManager.getPackageInfo(packageName, 0).versionCode


fun <T> Context.getPreference(key: String, default: T): T? = with(defaultSharedPreferences) {
    val res = when (default) {
        is Boolean -> getBoolean(key, default)
        is Float -> getFloat(key, default)
        is Int -> getInt(key, default)
        is Long -> getLong(key, default)
        is String -> getString(key, default)
        else -> null
    }
    res as? T
}


fun Context.getMarketIntent(): Intent {
    val uri = Uri.parse("market://details?id=" + packageName)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected(): Boolean? {
    return connectivityManager.activeNetworkInfo?.isConnected
}

fun Context.isScreenOn(): Boolean? {
    return powerManager?.isScreenOn
}

fun Context.isAppOnForeground(packageName: String = getPackageName()): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val appProcessed: List<ActivityManager.RunningAppProcessInfo>? = activityManager.runningAppProcesses
    appProcessed?.let {
        appProcessed.filter {
            it.processName == packageName && ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == it.importance
        }.map {
            return true
        }
    }
    return false
}


fun Context.launchApp(packageName: String): Intent {
    var intent = packageManager.getLaunchIntentForPackage(packageName) ?: getMarketIntent()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

fun Context.loadAnimation(id: Int): Animation {
    return AnimationUtils.loadAnimation(this, id)
}

fun <T> Context.putPreference(key: String, value: T) {
    defaultSharedPreferences.edit {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
        }
    }
}

fun Context.toggleSoftInput() {
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

var Activity.screenBrightness
    get() = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    set(value) {
        val lp = window.attributes
        lp.screenBrightness = value / 255f
        window.attributes = lp
    }


fun Activity.hideKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager.hideSoftInputFromWindow(currentView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}


fun Activity.showKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager.showSoftInput(currentView, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}


