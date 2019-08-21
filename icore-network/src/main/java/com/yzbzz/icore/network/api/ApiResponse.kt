package com.yzbzz.icore.network.api

import com.yzbzz.icore.network.vo.Resource

/**
 * Created by yzbzz on 2019-08-18.
 */
sealed class ApiResponse<T> {

    companion object {

        fun <T> create(code: Int?, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(code, error.message ?: "unknown error")
        }

        fun <T> create(response: Resource<T>): ApiSuccessResponse<T> {
            return ApiSuccessResponse(response.data, response.msg)
        }
    }
}

data class ApiSuccessResponse<T>(val data: T?, val msg: String?) : ApiResponse<T>()

data class ApiErrorResponse<T>(val code: Int?, val errorMsg: String) : ApiResponse<T>()
