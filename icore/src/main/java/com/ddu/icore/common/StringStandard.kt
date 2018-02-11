package com.ddu.icore.common

import java.util.regex.Pattern

fun String.formatMoney(): String {
    var payMoney = try {
        toDouble()
    } catch (e: Exception) {
        0.0
    }
    return format("%.2f", payMoney)
}

fun String.isChineseChar(): Boolean {
    val pattern = Pattern.compile("[\\u4e00-\\u9fa5]")
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isNumber(): Boolean {
    val p = Pattern.compile("\\-?[0-9]+(.[0-9]+)?")
    return p.matcher(this).matches()
}

fun String.isInteger(): Boolean {
    val p = Pattern.compile("\\-?[0-9]+")
    return p.matcher(this).matches()
}