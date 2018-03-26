package com.ddu.icore.ui.adapter.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

/**
 * Created by yzbzz on 16/4/3.
 */
abstract class AbsListViewAdapter<T, VH>(protected var mContext: Context, list: List<T>?) : BaseAdapter() {

    protected var mList: List<T>? = null

    protected var mLayoutInflater: LayoutInflater

    init {
        mList = list ?: ArrayList()
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    override fun getCount(): Int {
        return mList?.size ?: 0
    }

    override fun getItem(position: Int): Any? {
        return mList?.get(position) ?: position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        val viewHolder: VH
        if (convertView == null) {
            convertView = newView(parent)
            viewHolder = getViewHolder(convertView)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as VH
        }
        bindView(position, getItem(position) as T, viewHolder)
        return convertView
    }

    abstract fun newView(parent: ViewGroup): View

    abstract fun bindView(position: Int, t: T, holder: VH)

    abstract fun getViewHolder(convertView: View?): VH
}
