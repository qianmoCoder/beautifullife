package com.ddu.icore.util.sys

import com.ddu.icore.app.BaseApp
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by yzbzz on 2018/1/24.
 */
object PreferenceUtils {

    private val prefs by lazy {
        BaseApp.mContext.defaultSharedPreferences
    }


    fun <V> apply(name: String, value: V) = putPreference(name, value).apply()

    fun <V> commit(name: String, value: V) = putPreference(name, value).commit()

    fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res = when (default) {
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is String -> getString(name, default)
//            is Set<*> -> getStringSet(name, mutableSetOf())
            else -> throw IllegalArgumentException(
                    "This type can be saved into Preferences"
            )
        }
        res as T
    }

    private fun <V> putPreference(name: String, value: V) = with(prefs.edit()) {
        when (value) {
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            is Int -> putInt(name, value)
            is Long -> putLong(name, value)
            is String -> putString(name, value)
//            is Set<*> -> putStringSet(name, value)
            else -> throw IllegalArgumentException(
                    "This type can be saved into Preferences"
            )
        }
    }

}