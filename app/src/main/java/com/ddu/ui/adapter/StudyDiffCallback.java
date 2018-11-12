package com.ddu.ui.adapter;

import android.support.v7.util.DiffUtil;

import com.iannotation.model.RouteMeta;

public class StudyDiffCallback extends DiffUtil.ItemCallback<RouteMeta> {

    @Override
    public boolean areItemsTheSame(RouteMeta oldItem, RouteMeta newItem) {
        return oldItem.getClassName().equals(newItem.getClassName());
    }

    @Override
    public boolean areContentsTheSame(RouteMeta oldItem, RouteMeta newItem) {
        return oldItem == newItem;
    }
}
