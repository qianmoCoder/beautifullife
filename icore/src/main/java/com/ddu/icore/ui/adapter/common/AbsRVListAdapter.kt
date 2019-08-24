package com.ddu.icore.ui.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddu.icore.ui.widget.WrapperRecyclerViewAdapter

abstract class AbsRVListAdapter<T>(itemCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(itemCallback), WrapperRecyclerViewAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return wrappedAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        wrappedAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return wrappedAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return wrappedAdapter.getItemViewType(position)
    }
}
