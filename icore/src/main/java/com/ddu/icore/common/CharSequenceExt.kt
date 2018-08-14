package com.ddu.icore.common

import android.graphics.Color
import android.support.annotation.ColorInt
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import java.math.BigDecimal
import java.security.MessageDigest
import java.util.regex.Pattern

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

fun String?.parseColor(@ColorInt defaultColor: Int = Color.BLUE): Int {
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        defaultColor
    }
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

fun String.queryStringToKeysAndValues(): Map<String, String> {
    val result = LinkedHashMap<String, String>()
    var i = 0
    while (i < length) {
        var ampersandOffset: Int = indexOf('&', i)
        if (ampersandOffset == -1) {
            ampersandOffset = length
        }
        var equalsOffset = indexOf("=", i)
        if (equalsOffset == -1 || equalsOffset > ampersandOffset) {
            result[substring(i, ampersandOffset)] = ""
        } else {
            result[substring(i, equalsOffset)] = substring(equalsOffset + 1, ampersandOffset)
        }
        i = ampersandOffset + 1
    }
    return result
}


inline fun <reified T> String.toJsonFromFastJson(): T {
    return JSON.parseObject(this, T::class.java)
}

inline fun <reified T> String.toJsonFromGson(): T {
    return Gson().fromJson(this, T::class.java)
}