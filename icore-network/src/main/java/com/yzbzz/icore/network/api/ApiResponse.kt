package com.yzbzz.icore.network.api

/**
 * Created by yzbzz on 2019-08-18.
 */
sealed class ApiResponse<T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data = data)
    class Loading<T>(data: T? = null) : ApiResponse<T>(data = data)
    class Error<T>(code: Int, data: T? = null, message: String) :
        ApiResponse<T>(code = code, data = data, message = message)
}
