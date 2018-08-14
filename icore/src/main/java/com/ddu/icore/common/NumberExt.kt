package com.ddu.icore.common

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.math.BigDecimal

/**
 * Created by yzbzz on 2018/2/11.
 */

//fun Float.dp2px(context: Context) = this * (context.displayMetrics.densityDpi / 160)

fun Int.dp2px(context: Context) = this * context.density + 0.5

fun Int.sp2Px(context: Context) = this * context.scaledDensity + 0.5

fun Int.px2dp(context: Context) = this / context.density + 0.5

fun Int.px2sp(context: Context) = this / context.scaledDensity + 0.5

fun Float.dp2px(context: Context) = this * context.density

fun Float.sp2Px(context: Context) = this * context.scaledDensity

fun Float.px2dp(context: Context) = this / context.density

fun Float.px2sp(context: Context) = this / context.scaledDensity

fun Double.dp2px(context: Context) = this * context.density

fun Double.sp2Px(context: Context) = this * context.scaledDensity

fun Double.px2dp(context: Context) = this / context.density

fun Double.px2sp(context: Context) = this / context.scaledDensity

inline fun <reified T> Number.convert(value: Float): T {
    val result: Any = when (T::class) {
        Int::class -> toInt() * value + 0.5
        Float::class -> toFloat() * value
        Double::class -> toDouble() * value
        Long::class -> toLong() * value
        Short::class -> toShort() * value
        else -> throw IllegalArgumentException("Unsupported type")
    }
    return result as T
}

fun Double.formatMoney(): String {
    return String.format("%.2f", this)
}

fun Double.parseDecimals(newScale: Int, decimals: String): Double {
    return try {
        val temp = BigDecimal(decimals.toDouble())
        temp.setScale(newScale, BigDecimal.ROUND_HALF_UP).toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun Double.parseDecimals(newScale: Int, money: Double): Double {
    return try {
        val temp = BigDecimal(money)
        temp.setScale(newScale, BigDecimal.ROUND_HALF_UP).toDouble()
    } catch (e: Exception) {
        money
    }
}

fun Double.parseDecimals(): Double {
    return try {
        val temp = BigDecimal(this)
        temp.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
    } catch (e: Exception) {
        this
    }

}