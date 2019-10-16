package com.ddu.icore.common.ext

/**
 * Created by yzbzz on 2019-10-16.
 */
fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}

fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
    return !isNullOrEmpty()
}