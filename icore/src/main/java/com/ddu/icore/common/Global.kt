package com.ddu.icore.common

import com.alibaba.fastjson.JSON
import com.google.gson.Gson

/**
 * Created by yzbzz on 2018/3/12.
 */
infix fun <A, B, C> A.other(that: B): Unit {

}

inline fun <reified T : Any> Gson.fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

inline fun <reified T : Any> JSON.fromJson(json: String): T {
    return JSON.parseObject(json, T::class.java)
}

fun <T1, T2> ifNotNull(value1: T1?, value2: T2?, bothNotNull: (T1, T2) -> (Unit)) {
    if (value1 != null && value2 != null) {
        bothNotNull(value1, value2)
    }
}
