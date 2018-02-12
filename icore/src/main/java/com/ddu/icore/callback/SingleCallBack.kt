package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface SingleCallBack<in T> {

    fun execute(t: T)
}
