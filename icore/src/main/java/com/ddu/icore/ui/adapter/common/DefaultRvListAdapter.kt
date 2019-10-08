package com.ddu.icore.ui.adapter.common

import android.content.Context
import androidx.recyclerview.widget.DiffUtil

/**
 * Created by yzbzz on 2019-08-05.
 */
abstract class DefaultRvListAdapter<T>(
    private val mContext: Context,
    protected var mItems: List<T>,
    itemCallback: DiffUtil.ItemCallback<T>
) : AbsRVListAdapter<T>(itemCallback) {

    override fun getWrappedAdapter(): DefaultRVAdapter<T> {
        return object : DefaultRVAdapter<T>(mContext, mItems) {
            override fun getLayoutId(viewType: Int): Int {
                return getRvLayoutId(viewType)
            }

            override fun bindView(viewHolder: ViewHolder, data: T, position: Int) {
                bindRvView(viewHolder, data, position)
            }
        }
    }

    abstract fun getRvLayoutId(viewType: Int): Int

    abstract fun bindRvView(viewHolder: ViewHolder, data: T, position: Int)

}
