package com.ddu.ui.fragment.work.kotlin.motionlayout;

import android.content.Context;

import com.ddu.R;
import com.ddu.icore.navigation.Navigator;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.List;

public class MotionLayoutAdapter extends DefaultRVAdapter<Step> {

    public MotionLayoutAdapter(Context context, List<Step> items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.study_motion_layout_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, Step data, int position) {
        viewHolder.setText(R.id.header, data.getNumber());
        viewHolder.setText(R.id.description, data.getName());
        viewHolder.setText(R.id.caption, data.getCaption());

        int color;
        if (data.getHighlight()) {
            color = mContext.getResources().getColor(R.color.secondaryLightColor);
        } else {
            color = mContext.getResources().getColor(R.color.primaryTextColor);
        }
        viewHolder.setTextColor(R.id.header, color);
        viewHolder.setTextColor(R.id.description, color);
        viewHolder.itemView.setOnClickListener(v -> Navigator.startShowDetailActivity(mContext, data.getFragment()));
    }
}

