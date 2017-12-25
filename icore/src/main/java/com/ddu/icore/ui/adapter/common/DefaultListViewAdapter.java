package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yzbzz on 16/4/3.
 */
public abstract class DefaultListViewAdapter<T> extends AbsListViewAdapter<T, ViewHolder> {

    public DefaultListViewAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public View newView(ViewGroup parent) {
        int layoutId = getLayoutId();
        return LayoutInflater.from(mContext).inflate(layoutId, parent, false);
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(@NonNull View convertView) {
        return new ViewHolder(convertView);
    }

    public abstract int getLayoutId();
}
