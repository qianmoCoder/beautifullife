package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface Consumer1<in T> {

    fun accept(t: T)
}
