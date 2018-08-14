package com.ddu.ui.adapter;

import android.content.Context;
import android.view.View;

import com.ddu.R;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.view.OptionItemView;
import com.ddu.ui.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

public class UIRecycleViewAdapter extends DefaultRVAdapter<ItemEntity> implements ItemTouchHelperAdapter {

    private ItemClickListener itemClickListener;

    public UIRecycleViewAdapter(Context context, List<ItemEntity> items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_study_ui_rv_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ItemEntity data, final int position) {
        OptionItemView optionItemView = (OptionItemView) viewHolder.itemView;
        optionItemView.setContent(data.getTitle());
        optionItemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(data);
                }
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public interface ItemClickListener {
        void onItemClick(ItemEntity data);
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
