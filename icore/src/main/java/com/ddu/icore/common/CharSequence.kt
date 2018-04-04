package com.ddu.icore.common

import android.graphics.Color
import com.alibaba.fastjson.JSON
import com.ddu.icore.util.TextPhrase
import com.google.gson.Gson
import java.math.BigDecimal
import java.security.MessageDigest
import java.util.regex.Pattern

fun CharSequence.phrase(block: (TextPhrase) -> Unit): CharSequence? {
    val textPhrase = TextPhrase(this)
    block(textPhrase)
    return textPhrase.format()
}


fun CharSequence.phrase(vararg pair: Pair<String, Int>) {
    for ((index, p) in pair.withIndex()) {
        val textPhrase = TextPhrase(this)
        p.first.parseColor()?.let {
            if (index == 0) {
                textPhrase.innerFirstColor = it
            } else if (index == 1) {
                textPhrase.innerSecondColor = it
            }
        }

        if (index == 0) {
            textPhrase.innerFirstSize = p.second
        } else if (index == 1) {
            textPhrase.innerSecondSize = p.second
        }
    }

}


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

fun String?.parseColor(): Int? {
    return if (isNullOrEmpty()) {
        null
    } else {
        try {
            Color.parseColor(this)
        } catch (e: Exception) {
            null
        }
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

inline fun <reified T> String.toJson(): T {
    return JSON.parseObject(this, T::class.java)
}

inline fun <reified T> String.toJsonFromGson(): T {
    return Gson().fromJson(this, T::class.java)
}