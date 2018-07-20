package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface MultiCallBack<in A, in D> {

    fun execute(a: A, d: D)
}
