package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface Consumer2<in A, in D> {

    fun accept(a: A, d: D)
}
