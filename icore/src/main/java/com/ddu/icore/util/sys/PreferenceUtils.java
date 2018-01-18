package com.ddu.icore.util.sys;

import android.content.Context;
import android.content.SharedPreferences;

import com.ddu.icore.app.BaseApp;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yzbzz on 2017/12/11.
 */

public class PreferenceUtils {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private PreferenceUtils() {
        mContext = BaseApp.Companion.getContext();
        mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
    }

    private static class SingletonHolder {
        private static PreferenceUtils instance = new PreferenceUtils();
    }

    public static PreferenceUtils getInstance() {
        return SingletonHolder.instance;
    }

    public static <T> boolean commit(String key, T value) {
        return getEditor(key, value).commit();
    }

    public static <T> void apply(String key, T value) {
        getEditor(key, value).apply();
    }

    public static <T> T getValue(String key, T defValue) {
        Object value = getSharedPreferences().getAll().get(key);
        return value != null ? (T) value : defValue;
    }

    public static String getStringValue(String key) {
        return getValue(key, "");
    }

    public static int getIntValue(String key) {
        return getValue(key, -1);
    }

    public static boolean getBooleanValue(String key) {
        return getValue(key, false);
    }

    public static float getFloatValue(String key) {
        return getValue(key, -1f);
    }

    public static long getLongValue(String key) {
        return getValue(key, -1L);
    }

    public static Set<String> getSetValue(String key) {
        return getValue(key, new HashSet<String>());
    }

    private static <T> T getValue(String key, Class<T> tClass) {
        Object value = getSharedPreferences().getAll().get(key);
        if (value == null) {
            if (tClass == String.class) {
                value = "";
            } else if (tClass == Integer.class) {
                value = 1;
            } else if (tClass == Boolean.class) {
                value = false;
            } else if (tClass == Float.class) {
                value = 0f;
            } else if (tClass == Long.class) {
                value = 0L;
            } else if (tClass == Set.class) {
                value = new HashSet<>();
            }
        }
        return (T) value;
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).commit();
    }

    private static <T> SharedPreferences.Editor getEditor(String key, T value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Set) {
            HashSet hashSet = new HashSet((Set) value);
            editor.remove(key);
            editor.putStringSet(key, hashSet);
        } else {
            editor.putString(key, (String) value);
        }
        return editor;
    }

    public static SharedPreferences getSharedPreferences() {
        return getInstance().mSharedPreferences;
    }

}
