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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int EMPTY_VIEW = 0x0088;

    protected final Context mContext;
    protected LayoutInflater mLayoutInflater;

    private View mEmptyView;

    @NonNull
    protected final List<T> mItems;

    public CommonRecycleViewAdapter(Context context, @Nullable List<T> items) {
        mContext = context;
        mItems = (items != null) ? items : new ArrayList<T>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isShowEmptyView()) {
            view = mEmptyView;
        } else {
            view = getView(parent, viewType);
        }
        RecyclerView.ViewHolder holder = getViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowEmptyView()) {
            return;
        }
        bindView(holder, mItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (isShowEmptyView()) {
            return 1;
        }
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowEmptyView()) {
            return EMPTY_VIEW;
        }
        return getDefItemViewType(position);
    }

    public int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public void setEmptyView(int resId, ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
        setEmptyView(view);
    }

    public boolean isShowEmptyView() {
        if (null == mEmptyView) {
            return false;
        }
        if (mItems.size() != 0) {
            return false;
        }
        return true;
    }

    public abstract View getView(ViewGroup parent, int viewType);

    @NonNull
    public abstract RecyclerView.ViewHolder getViewHolder(View view);

    public abstract void bindView(RecyclerView.ViewHolder holder, T data, int position);
}
