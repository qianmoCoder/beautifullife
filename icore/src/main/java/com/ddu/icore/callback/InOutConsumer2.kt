package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface InOutConsumer2<in A, in B, out C> {

    fun accept(a: A, b: B): C
}
