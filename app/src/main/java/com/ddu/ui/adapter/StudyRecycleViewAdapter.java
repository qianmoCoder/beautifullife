package com.ddu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.help.ShapeInject;
import com.ddu.ui.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

public class StudyRecycleViewAdapter extends DefaultRecycleViewAdapter<ItemEntity> implements ItemTouchHelperAdapter {

    private ItemClickListener itemClickListener;
    private int radius;

    public StudyRecycleViewAdapter(Context context, List<ItemEntity> items) {
        super(context, items);
        radius = context.getResources().getDimensionPixelSize(R.dimen.dp_5);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_study_rv_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ItemEntity data, final int position) {
        TextView tvTitle = viewHolder.getView(R.id.tv_title);
        tvTitle.setText(data.getTitle());

        ShapeInject.inject(tvTitle)
                .setRadius(radius)
                .setBackgroundColor(getColor(data.getColor()))
                .background();

        viewHolder.setText(R.id.tv_title, data.getTitle());
        viewHolder.setText(R.id.tv_description, data.getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(data);
                }
            }
        });
    }

    private int getColor(String colorStr) {
        int color;
        try {
            color = Color.parseColor(colorStr);
        } catch (Exception e) {
            color = Color.BLUE;
        }
        return color;
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
