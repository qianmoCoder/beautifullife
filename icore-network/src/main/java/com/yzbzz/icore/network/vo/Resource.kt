package com.yzbzz.icore.network.vo

/**
 * Created by yzbzz on 2019-08-19.
 */
data class Resource<out T>(var code: Int? = null, val data: T? = null, val msg: String? = null) {

    companion object {

        fun isSuccessful(code: Int?): Boolean {
            return code in 200..300
        }

    }
}
