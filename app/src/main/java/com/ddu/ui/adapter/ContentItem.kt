package com.ddu.ui.adapter

import com.ddu.icore.ui.adapter.common.DBRVAdapter
import com.iannotation.model.RouteMeta

/**
 * Created by yzbzz on 2018/9/12.
 */
class ContentItem(itemEntity: RouteMeta) : DBRVAdapter.IItem {

    var colorString = itemEntity.color

    override fun getType(): Int = -1

    override fun variableId(): Int = -1

}