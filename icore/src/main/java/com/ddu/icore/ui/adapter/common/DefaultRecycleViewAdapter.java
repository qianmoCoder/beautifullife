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

package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class DefaultRecycleViewAdapter<T> extends CommonRecycleViewAdapter<T> {

    public DefaultRecycleViewAdapter(Context context, List<T> items) {
        super(context, items);
    }

    @Override
    public View getView(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        return LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder getViewHolder(@NonNull View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, T data, int position) {
        bindView((ViewHolder) holder, data, position);
    }

    public abstract int getLayoutId(int viewType);

    public abstract void bindView(ViewHolder viewHolder, T data, int position);
}
