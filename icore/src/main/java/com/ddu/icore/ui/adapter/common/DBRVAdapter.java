package com.ddu.icore.ui.adapter.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class DBRVAdapter extends RecyclerView.Adapter<DBRVAdapter.ItemViewHolder> {

    public static final int EMPTY_VIEW = 0x0088;

    protected final Context mContext;
    protected LayoutInflater mLayoutInflater;

    private View mEmptyView;

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
        View view;
        if (isShowEmptyView()) {
            view = mEmptyView;
        } else {
            view = getView(parent, viewType);
        }
        return ItemViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (isShowEmptyView()) {
            return;
        }
        holder.bindTo(mItems.get(position));
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
        return mItems.get(position).getType();
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
