package com.ddu.icore.ui.adapter.common;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.ddu.icore.ui.widget.WrapperRecyclerViewAdapter;

public abstract class AbsRVListAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> implements WrapperRecyclerViewAdapter {

    public AbsRVListAdapter(DiffUtil.ItemCallback<T> itemCallback) {
        super(itemCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getWrappedAdapter().onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        getWrappedAdapter().onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getWrappedAdapter().getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getWrappedAdapter().getItemViewType(position);
    }
}
