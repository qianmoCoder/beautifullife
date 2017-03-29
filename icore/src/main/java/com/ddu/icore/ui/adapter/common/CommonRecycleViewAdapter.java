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
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context mContext;
    @NonNull
    protected final List<T> mItems;
    boolean canLoop = true;
    int size;

    public CommonRecycleViewAdapter(Context context, @Nullable List<T> items) {
        mContext = context;
        mItems = (items != null) ? items : new ArrayList<T>();
        size = mItems.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, viewType);
        RecyclerView.ViewHolder holder = getViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int realPosition = toRealPosition(position);
        bindView(holder, mItems.get(realPosition), position);
    }

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    public int getRealCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemCount() {
        return canLoop ? Integer.MAX_VALUE : getRealCount();
    }

    public abstract View getView(ViewGroup parent, int viewType);

    @NonNull
    public abstract RecyclerView.ViewHolder getViewHolder(View view);

    public abstract void bindView(RecyclerView.ViewHolder holder, T data, int position);
}
