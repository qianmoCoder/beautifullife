package com.ddu.icore.callback

/**
 * Created by yzbzz on 2018/2/9.
 */

interface OutConsumer1<out T> {

    fun accept(): T
}
