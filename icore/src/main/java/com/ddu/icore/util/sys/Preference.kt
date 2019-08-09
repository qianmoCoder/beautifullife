package com.ddu.icore.util.sys

import android.content.Context
import com.ddu.icore.common.ext.defaultSharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by yzbzz on 2018/1/24.
 */
class Preference<T>(val context: Context, val name: String, val default: T) : ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.defaultSharedPreferences
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name, default)
    }

    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException(
                    "This type can be saved into Preferences"
            )
        }
        res as T
    }

    private fun <V> apply(name: String, value: V) = putPreference(name, value).apply()

    private fun <V> commit(name: String, value: V) = putPreference(name, value).commit()

    private fun <V> putPreference(name: String, value: V) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException(
                    "This type can be saved into Preferences"
            )
        }
    }

}