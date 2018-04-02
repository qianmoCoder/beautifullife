package com.ddu.icore.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ddu.icore.R;
import com.ddu.icore.entity.ShareEntity;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.List;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class ShareAdapter extends DefaultRecycleViewAdapter<ShareEntity> {

    private OnClickListener onClickListener;

    public ShareAdapter(Context context, List<ShareEntity> items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_share_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final ShareEntity data, final int position) {

        ImageView imageView = viewHolder.getView(R.id.iv_share);

        int size = getMItems().size();
        int height;

        int imgHeight;
        if (size < 4) {
            height = (int) getMContext().getResources().getDimension(R.dimen.dp_150);
            imgHeight = (int) getMContext().getResources().getDimension(R.dimen.dp_70);
        } else {
            height = (int) getMContext().getResources().getDimension(R.dimen.dp_120);
            imgHeight = (int) getMContext().getResources().getDimension(R.dimen.dp_60);
        }
        viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        imageView.setLayoutParams(new LinearLayout.LayoutParams(imgHeight, imgHeight));

        viewHolder.setText(R.id.tv_share, data.getName());
        if (null != imageView) {
            imageView.setImageResource(data.getResId());
        }
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
