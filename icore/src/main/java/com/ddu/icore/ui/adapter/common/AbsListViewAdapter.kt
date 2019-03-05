package com.ddu.icore.ui.adapter.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

/**
 * Created by yzbzz on 16/4/3.
 */
abstract class AbsListViewAdapter<T, VH>(protected var mContext: Context, var dataList: List<T>) : BaseAdapter() {

    init {
        var mLayoutInflater = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): T {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var itemView = convertView
        val viewHolder: VH
        if (itemView == null) {
            itemView = newView(parent)
            viewHolder = getViewHolder(convertView)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as VH
        }
        bindView(position, getItem(position), viewHolder)
        return convertView
    }

    abstract fun newView(parent: ViewGroup): View

    abstract fun bindView(position: Int, t: T, holder: VH)

    abstract fun getViewHolder(convertView: View?): VH
}
