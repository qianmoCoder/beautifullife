package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 16/4/3.
 */
public abstract class AbsListViewAdapter<T, VH> extends BaseAdapter {

    protected Context mContext;

    @Nullable
    protected List<T> mList;

    protected LayoutInflater mLayoutInflater;

    public AbsListViewAdapter(Context context, @Nullable List<T> list) {
        mContext = context;
        mList = (null != list) ? list : new ArrayList<T>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null != mList ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mList ? mList.get(position) : position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        VH viewHolder;
        if (convertView == null) {
            convertView = newView(parent);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        bindView(position, (T) getItem(position), viewHolder);
        return convertView;
    }

    public abstract View newView(ViewGroup parent);

    public abstract void bindView(int position, T t, VH holder);

    @NonNull
    public abstract VH getViewHolder(View convertView);
}
