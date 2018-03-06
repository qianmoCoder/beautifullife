package com.ddu.test

/**
 * Created by yzbzz on 2018/3/2.
 */
open class Father(val _nickname: String, val isSubscribed: Boolean = true) {

    var t = hashMapOf<String, String>()
    fun main() {
        val s = Sun()
    }

    inner class Sun {
        fun temp() = this@Father.main()

    }

    companion object {
        fun bar() {

        }
    }

}