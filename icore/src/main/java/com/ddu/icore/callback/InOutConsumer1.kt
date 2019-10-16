package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface InOutConsumer1<in A, out B> {

    fun accept(a: A): B
}
