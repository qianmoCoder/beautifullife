package com.ddu.icore.util.sys

import com.ddu.icore.app.BaseApp
import com.ddu.icore.common.isSetOf
import org.jetbrains.anko.defaultSharedPreferences

/**
 * Created by yzbzz on 2018/1/24.
 */
object PreferenceUtils {

    val prefs by lazy {
        BaseApp.mContext.defaultSharedPreferences
    }


    fun <V> apply(name: String, value: V) = putPreference(name, value).apply()

    fun <V> commit(name: String, value: V) = putPreference(name, value).commit()

    inline fun <reified T> findPreference(name: String, default: T): T? = with(prefs) {
        val res: Any? = when (default) {
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            is Int -> getInt(name, default)
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Set<*> -> when {
                default.isSetOf<String>() -> getStringSet(name, default as Set<out String>)
                else -> setOf()
            }
            else -> throw IllegalArgumentException("Unsupported type")
        }
        res as? T
    }

    private fun <V> putPreference(key: String, value: V) = with(prefs.edit()) {
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