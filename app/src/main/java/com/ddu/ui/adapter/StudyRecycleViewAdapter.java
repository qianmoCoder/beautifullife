package com.ddu.ui.adapter;

import android.content.Context;
import android.view.View;

import com.ddu.R;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.ui.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

public class StudyRecycleViewAdapter extends DefaultRecycleViewAdapter<ItemEntity> implements ItemTouchHelperAdapter {

    private ItemClickListener itemClickListener;

    public StudyRecycleViewAdapter(Context context, List<ItemEntity> items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.rv_item_linear;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ItemEntity data, int position) {
        final ItemEntity studyContent = data;
        viewHolder.setText(R.id.tv_detail, data.getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(data);
                }
            }
        });

//        FlexboxLayout flexboxLayout = viewHolder.getView(R.id.fbl_content);
//
//        String[] tags = studyContent.getDescription().split(" ");
//
//        for (String tag : tags) {
//            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.fragment_tag_view, null);
//            textView.setText(tag);
//
//            GradientDrawable gd = new GradientDrawable();
//            gd.setCornerRadius(dimen);
//            gd.setColor(color);
//
//            textView.setBackgroundDrawable(gd);
//            flexboxLayout.addView(textView, layoutParams);
//        }
//        viewHolder.setText(R.id.tv_title, data.getMTitle());
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
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
