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

public abstract class AbsRVAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int EMPTY_VIEW = 0x0088;

    protected final Context mContext;
    protected LayoutInflater mLayoutInflater;

    private View mEmptyView;

    @NonNull
    protected final List<T> mItems;

    public AbsRVAdapter(Context context, @Nullable List<T> items) {
        mContext = context;
        mItems = (items != null) ? items : new ArrayList<T>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isShowEmptyView()) {
            view = mEmptyView;
        } else {
            view = getView(parent, viewType);
        }
        return getViewHolder(view);
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
        return mItems.size();
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
