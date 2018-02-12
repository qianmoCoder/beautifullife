package com.ddu.icore.util

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.ArrayMap
import com.ddu.icore.app.BaseApp

/**
 * Created by yzbzz on 2017/9/20.
 */

object DnsConfig {

    var urlMaps = ArrayMap<String, String>()

    val BUILD_TYPE = buildType

    val baseUrl: String?
        get() {
            var url = urlMaps[BUILD_TYPE]
            if (TextUtils.isEmpty(url)) {
                url = "http://default"
            }
            return url
        }

    val buildType: String
        get() = getMetaData(BaseApp.getContext()).getString("BUILD_TYPE", "debug")

    init {
        urlMaps["qa"] = "http://qa"
        urlMaps["release"] = "http://release"
        urlMaps["debug"] = "http://debug"
    }


    fun getMetaData(context: Context): Bundle {
        return context.applicationInfo.metaData
    }
}
