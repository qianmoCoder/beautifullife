package com.ddu.icore.vo

/**
 * Created by yzbzz on 2019-08-19.
 */
data class Resource<out T>(val status: Status, val data: T?, val exception: Exception?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, exception)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    override fun toString(): String {
        return when (status) {
            Status.LOADING -> "Loading[]"
            Status.SUCCESS -> "Success[data=$data]"
            Status.ERROR -> "Error[exception=$exception]"
        }
    }
}
