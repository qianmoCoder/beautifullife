package com.ddu.icore.common.ext

import android.util.SparseArray
import androidx.core.util.isEmpty

/**
 * Created by yzbzz on 2019-10-16.
 */
fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

fun <T> SparseArray<T>?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

fun <T> SparseArray<T>?.hasKey(key: Int): Boolean {
    return if (isNullOrEmpty()) {
        false
    } else {
        val index = this?.indexOfKey(key)
        index ?: -1 >= 0
    }
}