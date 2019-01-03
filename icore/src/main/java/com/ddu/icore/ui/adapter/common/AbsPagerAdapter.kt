package com.ddu.icore.ui.adapter.common

import android.content.ClipData
import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yzbzz on 2018/2/12.
 */
abstract class AbsPagerAdapter(context: Context, data: ClipData) : androidx.viewpager.widget.PagerAdapter() {

    private val mLayoutInflater = LayoutInflater.from(context)
    private val mData = data

    override fun isViewFromObject(view: View, objectView: Any): Boolean {
        return view == objectView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = getView(position, null, container)
        container.addView(view)
        return view
    }

    private fun getView(position: Int, view: View?, container: ViewGroup): View {
        var holder: ViewHolder?
        var view = view
        if (view == null) {
            view = newView(position, container)
            holder = ViewHolder(view)
        } else {
            holder = view.tag as ViewHolder
        }
        bindView(position, holder)
        return view
    }


    override fun getCount(): Int {
        return mData.itemCount
    }

    abstract fun newView(position: Int, container: ViewGroup): View

    abstract fun bindView(position: Int, holder: ViewHolder)

}
