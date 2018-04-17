package com.ddu.icore.entity

/**
 * Created by yzbzz on 2017/3/31.
 */

class BottomItemEntity(var name: String = "", var resId: Int = 0, var data: String = "", var cb: ((BottomItemEntity) -> Unit)? = null)
