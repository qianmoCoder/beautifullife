package com.ddu.model

/**
 * Created by yzbzz on 2020/1/10.
 */
const val TYPE_TITLE = 0x10
const val TYPE_CONTENT = 0x20

data class WorkEntity(
        var id: String,
        var type: Int,
        var title: String = "",
        var number: String = "",
        val content: String = "",
        val fragmentName: String? = null
)