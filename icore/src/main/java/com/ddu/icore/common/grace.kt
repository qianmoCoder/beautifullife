package com.ddu.icore.common

/**
 * Created by yzbzz on 2018/1/18.
 */
fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}