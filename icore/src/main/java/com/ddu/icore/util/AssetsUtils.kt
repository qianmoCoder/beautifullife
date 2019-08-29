package com.ddu.icore.util

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by yzbzz on 2019-08-10.
 */
object AssetsUtils {

    fun getString(ctx: Context): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = ctx.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open("car_brand.json")
                )
            )
            bf.use { r ->
                r.lineSequence().forEach {
                    stringBuilder.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

}