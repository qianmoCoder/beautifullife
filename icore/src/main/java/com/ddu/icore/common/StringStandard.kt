package com.ddu.icore.common

import com.google.gson.Gson
import java.math.BigDecimal
import java.security.MessageDigest
import java.util.regex.Pattern

inline fun <reified T> String.toJson(): T {
    return Gson().fromJson(this, T::class.java)
}

//inline fun <reified T> String.toJso1n(): T {
//    JSON.parseObject<T::class.java>()
//    return JSON.parseObject(this, T::class.java)
//}

fun String.md5(): String {
    val digestInstance = MessageDigest.getInstance("MD5")
    digestInstance.update(toByteArray(charset("utf-8")))
    val md = digestInstance.digest()
    val sb = StringBuffer()
    for (i in md.indices) {
        val temp: Int = (md[i]).toInt() and 0xff
        if (temp < 16)
            sb.append("0")
        sb.append(Integer.toHexString(temp))
    }
    return sb.toString().toUpperCase()
}

fun String.parseDecimals(): String {
    return try {
        var temp = BigDecimal(this).setScale(2, BigDecimal.ROUND_UP)
        return temp.toString()
    } catch (e: Exception) {
        this
    }

}

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