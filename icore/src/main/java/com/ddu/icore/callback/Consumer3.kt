package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface Consumer3<in A, in D, in P> {

    fun accept(a: A, d: D, p: P)
}
