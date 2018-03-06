package com.ddu.test

/**
 * Created by yzbzz on 2018/3/2.
 */
class Client {
    fun Main() {
        val t = Father("").Sun()
        t?.run {
            temp()
        }
        Father.bar()
        val temp = listOf(1, 2, 3)
        temp.maxBy { it }
    }
}