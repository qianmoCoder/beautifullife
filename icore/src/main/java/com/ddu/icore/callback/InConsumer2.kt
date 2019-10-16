package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface InConsumer2<in A, in B> {

    fun accept(a: A, b: B)
}
