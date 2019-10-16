package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface InConsumer3<in A, in B, in C> {

    fun accept(a: A, b: B, c: C)
}
