package com.ddu.icore.repository

import com.ddu.icore.util.safeApiCall
import com.ddu.icore.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by yzbzz on 2019-11-08.
 */
open class BaseRepository() {

    suspend fun <T : Any> executeOnIO(call: suspend () -> Resource<T>, errorMessage: String? = "") =
        withContext(Dispatchers.IO) {
            safeApiCall(call, errorMessage ?: "")
        }
}
