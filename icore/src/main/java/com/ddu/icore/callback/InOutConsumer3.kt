package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface InOutConsumer3<in A, in B, in C, out D> {

    fun accept(a: A, b: B, c: C): D
}
