package com.ddu.icore.util

import com.ddu.icore.vo.Resource

/**
 * Created by yzbzz on 2019-11-08.
 */
/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Resource.error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(
    call: suspend () -> Resource<T>,
    errorMessage: String
): Resource<T> {
    return try {
        call()
    } catch (e: Exception) {
        Resource.error(Exception(errorMessage, e), null)
    }
}