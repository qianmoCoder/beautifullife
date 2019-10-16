package com.ddu.icore

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper

/**
 * Created by yzbzz on 2018/8/21.
 */
object ICore {

    private var sApp: Application? = null

    private var sMainHandler: Handler? = null

    private var isInit = false

    val context: Context
        get() = app.applicationContext

    val app: Application
        get() {
            if (null == sApp) {
                throw RuntimeException("You must call ICore.init first")
            }
            return sApp!!
        }

    val mainHandler: Handler
        @Synchronized get() {
            if (null == sMainHandler) {
                sMainHandler = Handler(Looper.getMainLooper())
            }
            return sMainHandler!!
        }

    fun init(application: Application) {
        if (isInit) {
            return
        }
        isInit = true
        sApp = application
    }
}
