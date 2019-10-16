package com.ddu.icore.util.sys

import android.content.SharedPreferences
import com.ddu.icore.ICore
import com.ddu.icore.common.ext.defaultSharedPreferences
import com.ddu.icore.common.ext.isSetOf

/**
 * Created by yzbzz on 2018/1/24.
 */
object PreferenceUtils {

    val prefs: SharedPreferences by lazy {
        ICore.context.defaultSharedPreferences
    }

    fun <V> apply(key: String, value: V) = putPreference(key, value).apply()

    fun <V> commit(key: String, value: V) = putPreference(key, value).commit()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> findPreference(key: String, defaultValue: T): T? = with(prefs) {
        val res: Any? = when (defaultValue) {
            is Boolean -> getBoolean(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Set<*> -> when {
                defaultValue.isSetOf<String>() -> getStringSet(key, defaultValue as Set<String>)
                else -> setOf()
            }
            else -> throw IllegalArgumentException("Unsupported type")
        }
        res as? T
    }

    @Suppress("UNCHECKED_CAST")
    private fun <V> putPreference(key: String, value: V) = with(prefs.edit()) {
        when (value) {
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Set<*> -> when {
                value.isSetOf<String>() -> putStringSet(key, value as Set<String>)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
    }

}