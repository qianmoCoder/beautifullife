package com.ddu.icore.util

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Created by yzbzz on 2018/3/7.
 */
class FileCacheManager(val name: String) {

    val email: String by lazy {
        ""
    }

    private fun loadFile(file: File): String {
        return async(UI) {
            bg {
                val bufferedReader = BufferedReader(InputStreamReader(FileInputStream(file)))
                val tempStr: String = ""
                val sb = StringBuilder()
                bufferedReader.use {
                    while ((tempStr == readLine()) != null) {
                        sb.append(tempStr)
                    }
                }
                sb.toString()
            }.await()
        }.getCompleted()
    }

}
