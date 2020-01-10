package com.ddu.ui.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.iannotation.model.RouteMeta;

import java.util.Objects;

public class StudyDiffCallback extends DiffUtil.ItemCallback<RouteMeta> {

    @Override
    public boolean areItemsTheSame(RouteMeta oldItem, RouteMeta newItem) {
        return oldItem.getClassName().equals(newItem.getClassName());
    }

    @Override
    public boolean areContentsTheSame(RouteMeta oldItem, RouteMeta newItem) {
        return Objects.equals(oldItem,newItem);
    }
}
