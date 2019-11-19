package com.ddu.icore.api

import com.ddu.icore.vo.Resource

/**
 * Created by yzbzz on 2019-08-18.
 */
sealed class ApiResponse<T> {

    companion object {

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Resource<T>): ApiSuccessResponse<T> {
            return ApiSuccessResponse(response.data)
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val data: T?) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMsg: String) : ApiResponse<T>()
