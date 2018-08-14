package com.ddu.icore.dialog;

import android.content.Context;
import android.view.View;

import com.ddu.icore.R;
import com.ddu.icore.entity.BottomItemEntity;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.List;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class DefaultLinearBottomAdapter extends DefaultRecycleViewAdapter<BottomItemEntity> {

    private OnClickListener onClickListener;


    public DefaultLinearBottomAdapter(Context context, List<BottomItemEntity> items) {
        super(context, items);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.rv_item_linear;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final BottomItemEntity data, final int position) {
        viewHolder.setText(R.id.tv_title, data.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClickListener) {
                    onClickListener.onClick(data, position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener<T> {
        void onClick(T data, int position);
    }
}
