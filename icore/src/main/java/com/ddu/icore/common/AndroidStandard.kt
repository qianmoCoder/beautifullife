package com.ddu.icore.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by yzbzz on 2018/1/18.
 */
val Context.inputMethodManager: InputMethodManager?
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

//inline fun <T> Activity.startAcitvity() {
//    val intent = Intent(this,T::class.java)
//    startActivity(intent)
//}

fun Activity.hideKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager?.hideSoftInputFromWindow(currentView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}

fun Activity.showKeyboard(view: View?): Boolean? {
    val currentView = currentFocus ?: view
    currentView?.let {
        return inputMethodManager?.showSoftInput(currentView, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
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
