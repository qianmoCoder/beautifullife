package com.ddu.icore.common

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.PowerManager
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.telephony.TelephonyManager
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by yzbzz on 2018/1/18.
 */
val Context.ctx
    get() = this

val Context.activityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

val Context.connectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.clipboardManager
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

val Context.inputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

val Context.powerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager

val Context.telephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

val Context.notificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

val Context.defaultSharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)

val Context.displayMetrics
    get() = resources.displayMetrics

val Activity.act
    get() = this

val Fragment.ctx
    get() = context!!

val Fragment.act
    get() = activity!!

inline fun <reified T : Activity> Context.startActivity() = startActivity(Intent(this, T::class.java))

inline fun <reified T : Activity> Fragment.startActivity() = startActivity(Intent(ctx, T::class.java))

inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id)

inline fun <reified T : View> View.find(id: Int): T = findViewById(id)
