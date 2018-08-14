package com.ddu.icore.rx.bus

/**
 * Created by yzbzz on 2018/2/9.
 */

interface ActionCallBack<in T> {

    fun execute(t: T)
}
