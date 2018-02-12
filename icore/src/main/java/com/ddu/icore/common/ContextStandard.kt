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
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.connectivityManager
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.powerManager

/**
 * Created by yzbzz on 2018/1/18.
 */

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.versionName
    get() = packageManager.getPackageInfo(packageName,0).versionName
val Context.versionCode
    get() = packageManager.getPackageInfo(packageName,0).versionCode

var Activity.screenBrightness
    get() = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
    set(value) {
        val lp = window.attributes
        lp.screenBrightness = value / 255f
        window.attributes = lp
    }

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected(): Boolean? {
    return connectivityManager.activeNetworkInfo?.isConnected
}

fun Context.isScreenOn(): Boolean? {
    return powerManager?.isScreenOn
}

//inline fun <T> Activity.startAcitvity() {
//    val intent = Intent(this,T::class.java)
//    startActivity(intent)
//}

fun Context.getMarketIntent(): Intent {
    val uri = Uri.parse("market://details?id=" + packageName)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

fun Context.launchApp(packageName: String): Intent {
    var intent = packageManager.getLaunchIntentForPackage(packageName) ?: getMarketIntent()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
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

fun Context.toggleSoftInput() {
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}
