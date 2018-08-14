package com.ddu.config

import com.ddu.config.url.UrlConfig

/**
 * Created by yzbzz on 2017/12/21.
 */

class ConfigManager {

    companion object {
        fun get() = SingletonHolder.instance
        val instance = SingletonHolder.instance
    }


    val urlConfig: UrlConfig?
        get() = null

    private object SingletonHolder {
        val instance = ConfigManager()
    }
}
