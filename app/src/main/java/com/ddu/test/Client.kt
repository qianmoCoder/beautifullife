package com.ddu.test

import java.net.Socket

/**
 * Created by yzbzz on 2018/3/2.
 */
class Client {

    companion object {

        operator fun invoke(temp: Int): Father {
            return when(temp) {
                1 -> Sun1()
                else -> Sun1()
            }
        }
    }
    fun main() {
        val t = Father("").Sun()
        t?.run {
            temp()
        }
        Father.bar()
        val temp = listOf(1, 2, 3)
        temp.maxBy { it }
        val temp1 = Client(1)
        print(temp1.Sun())
        Socket().use {  }
//        for(i in 1..5)
    }
}