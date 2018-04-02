/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddu.icore.ui.adapter.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class CommonRecycleViewAdapter<T>(var context: Context, var items: List<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mLayoutInflater = LayoutInflater.from(context)

    private var emptyView: View? = null

    private val isShowEmptyView: Boolean
        get() {
            return (null != emptyView) && !items.isNotEmpty()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = if (isShowEmptyView) {
            emptyView
        } else {
            getView(parent, viewType)
        }
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isShowEmptyView) {
            return
        }
        bindView(holder, items[position], position)
    }

    override fun getItemCount(): Int {
        return if (isShowEmptyView) {
            1
        } else items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowEmptyView) {
            EMPTY_VIEW
        } else super.getItemViewType(position)
    }

    fun setEmptyView(resId: Int, viewGroup: ViewGroup) {
        val view = LayoutInflater.from(viewGroup.context).inflate(resId, viewGroup, false)
        emptyView = view
    }

    abstract fun getView(parent: ViewGroup, viewType: Int): View

    abstract fun getViewHolder(view: View?): RecyclerView.ViewHolder

    abstract fun bindView(holder: RecyclerView.ViewHolder, data: T, position: Int)

    companion object {
        const val EMPTY_VIEW = 0x0088
    }
}
