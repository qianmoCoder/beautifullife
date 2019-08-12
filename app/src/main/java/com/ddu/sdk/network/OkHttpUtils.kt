package com.ddu.sdk.network

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

/**
 * Created by yzbzz on 2018/2/26.
 */
object OkHttpUtils {

    fun appendUrl(url: String, urlParams: HashMap<String, String>): String {
        if (urlParams.size <= 0) {
            return url
        }
        val httpUrl: HttpUrl? = url.toHttpUrlOrNull()
        if (null == httpUrl) {
            return url
        }
        val urlBuilder = httpUrl?.newBuilder()
        for ((key, value) in urlParams.entries) {
            urlBuilder.addQueryParameter(key, value)
        }
        return urlBuilder.build().toString()
    }
}