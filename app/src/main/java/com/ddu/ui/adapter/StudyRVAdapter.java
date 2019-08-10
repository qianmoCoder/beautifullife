package com.ddu.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.callback.Consumer1;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.help.ShapeInject;
import com.ddu.ui.helper.ItemTouchHelperAdapter;
import com.iannotation.model.RouteMeta;

import java.util.Collections;
import java.util.List;

public class StudyRVAdapter extends DefaultRVAdapter<RouteMeta> implements ItemTouchHelperAdapter {

    private Consumer1<RouteMeta> consumer1;
    private int radius;

    public StudyRVAdapter(Context context, List<RouteMeta> items) {
        super(context, items);
        radius = context.getResources().getDimensionPixelSize(R.dimen.dp_5);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.i_fragment_study_rv_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final RouteMeta data, final int position) {
        TextView tvTitle = viewHolder.getView(R.id.tv_title);
        tvTitle.setText(data.getText());

        ShapeInject.inject(tvTitle)
                .setRadius(radius)
                .setBackgroundColor(getColor(data.getColor()))
                .background();

        viewHolder.setText(R.id.tv_description, data.getDescription());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != consumer1) {
                    consumer1.accept(data);
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


    public void setItemClickListener(Consumer1<RouteMeta> consumer1) {
        this.consumer1 = consumer1;
    }
}
