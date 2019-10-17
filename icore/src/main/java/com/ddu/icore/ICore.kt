package com.ddu.icore

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper

/**
 * Created by yzbzz on 2018/8/21.
 */
@SuppressLint("StaticFieldLeak")
object ICore {

    private var sContext: Context? = null

    private var sMainHandler: Handler? = null

    private var isInit = false

    @JvmStatic
    val context: Context
        get() {
            if (null == sContext) {
                throw RuntimeException("You must call ICore.init first")
            }
            return sContext!!
        }

    @JvmStatic
    val mainHandler: Handler
        @Synchronized get() {
            if (null == sMainHandler) {
                sMainHandler = Handler(Looper.getMainLooper())
            }
            return sMainHandler!!
        }

    fun init(context: Context?) {
        if (isInit) {
            return
        }
        isInit = true
        sContext = context?.applicationContext
    }
}
