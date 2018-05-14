package com.ddu.icore.common

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.app.Fragment
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresPermission
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.edit
import com.ddu.icore.ui.activity.ShowDetailActivity
import com.ddu.icore.util.ToastUtils
import com.ddu.icore.util.sys.PreferenceUtils
import org.jetbrains.anko.*

/**
 * Created by yzbzz on 2018/1/18.
 */

val Context.mateData
    get() = applicationInfo.metaData

val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

val Context.density
    get() = displayMetrics.density

val Context.scaledDensity
    get() = displayMetrics.scaledDensity

val Context.versionName
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode
    get() = packageManager.getPackageInfo(packageName, 0).versionCode


inline fun <reified T> Context.findPreference(key: String, default: T): T? = PreferenceUtils.findPreference(key, default)

inline fun <reified T> Context.pPreference(key: String, value: T) {
    defaultSharedPreferences.edit {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Set<*> -> when {
                value.isSetOf<String>() -> putStringSet(key, value as Set<out String>)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
    }
}

fun Context.clipText(text: String = "") {
    clipboardManager.primaryClip = ClipData.newPlainText("clipText", text)
}

fun Context.getMarketIntent(): Intent {
    val uri = Uri.parse("market://details?id=" + packageName)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected() = connectivityManager.activeNetworkInfo?.isConnected ?: false

fun Context.isScreenOn(): Boolean? = powerManager.isScreenOn

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

fun Context.loadAnimation(id: Int): Animation = AnimationUtils.loadAnimation(this, id)

fun <T> Context.putPreference(key: String, value: T) = PreferenceUtils.apply(key, value)

fun Context.toggleSoftInput() = inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

inline fun <reified T : Fragment> Context.startFragment(bundle: Bundle = Bundle()) {
    startFragment(T::class.java.name, bundle)
}

fun Context.startFragment(fragmentName: String, bundle: Bundle = Bundle()) {
    startActivity(ShowDetailActivity.getShowDetailIntent(this, fragmentName, bundle))
}

fun Context.startBrowser(url: String = "") {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val handlers = packageManager.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
    if (handlers == null || handlers!!.size == 0) {
        ToastUtils.showToast("browser not found")
    } else {
        startActivity(intent)
    }
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


