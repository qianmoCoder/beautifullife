package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DBRVAdapter extends RecyclerView.Adapter<DBRVAdapter.ItemViewHolder> {

    protected final Context mContext;
    protected LayoutInflater mLayoutInflater;

    @NonNull
    protected final List<IItem> mItems;

    public DBRVAdapter(Context context, @Nullable List<IItem> items) {
        mContext = context;
        mItems = (items != null) ? items : new ArrayList<IItem>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindTo(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(DBRVAdapter.IItem item) {
            binding.setVariable(item.variableId(), item);
            binding.executePendingBindings();
        }

        public static ItemViewHolder create(ViewGroup parent, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
            return new ItemViewHolder(binding);
        }
    }

    public interface IItem {
        int getType();

        int variableId();
    }
}
