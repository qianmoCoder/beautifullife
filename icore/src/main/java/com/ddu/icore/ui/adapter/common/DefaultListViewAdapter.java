package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import androidx.annotation.NonNull;
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

    @NonNull
    @Override
    public View newView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(getMContext()).inflate(getLayoutId(), parent, false);
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    public abstract int getLayoutId();
}
