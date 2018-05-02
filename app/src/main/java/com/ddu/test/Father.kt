package com.ddu.test

import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

/**
 * Created by yzbzz on 2018/3/2.
 */
open class Father(val _nickname: String = "", val isSubscribed: Boolean = true) {

    var t = hashMapOf<String, String>()
    fun main() {
        val list = listOf(1, 2, 3, 4, 5, 6)
       val job1 =  launch(UI) {

        }
        Sun::class.java
        async {  }
        launch(Unconfined) {  }
    }

    inner class Sun {
        fun temp() = this@Father.main()
    }

    fun bar() {

    }

    companion object

}