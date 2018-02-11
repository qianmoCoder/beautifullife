package com.ddu.icore.common

import java.math.BigDecimal

/**
 * Created by yzbzz on 2018/2/11.
 */

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