package com.ddu.icore.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ddu.icore.R;
import com.ddu.icore.entity.BottomItemEntity;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import java.util.List;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class DefaultGridBottomAdapter extends DefaultRVAdapter<BottomItemEntity> {

    private OnClickListener onClickListener;

    private int mHeight;
    private int mImgHeight;

    private int mSmallHeight;
    private int mSmallImgHeight;

    private int mColumnCount = 4;

    public DefaultGridBottomAdapter(Context context, List<BottomItemEntity> items) {
        super(context, items);
        mHeight = (int) mContext.getResources().getDimension(R.dimen.dp_120);
        mImgHeight = (int) mContext.getResources().getDimension(R.dimen.dp_40);

        mSmallHeight = (int) mContext.getResources().getDimension(R.dimen.dp_90);
        mSmallImgHeight = (int) mContext.getResources().getDimension(R.dimen.dp_30);
    }

    public DefaultGridBottomAdapter(Context context, List<BottomItemEntity> items, int columnCount) {
        super(context, items);
        mHeight = (int) mContext.getResources().getDimension(R.dimen.dp_120);
        mImgHeight = (int) mContext.getResources().getDimension(R.dimen.dp_40);

        mSmallHeight = (int) mContext.getResources().getDimension(R.dimen.dp_90);
        mSmallImgHeight = (int) mContext.getResources().getDimension(R.dimen.dp_30);

        mColumnCount = columnCount;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.rv_item_grid;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final BottomItemEntity data, final int position) {

        ImageView imageView = viewHolder.getView(R.id.iv_left_icon);

        int size = mItems.size();

        int height;
        int imgHeight;

        if (size < mColumnCount) {
            height = mHeight;
            imgHeight = mImgHeight;
        } else {
            height = mSmallHeight;
            imgHeight = mSmallImgHeight;
        }
        viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        imageView.setLayoutParams(new LinearLayout.LayoutParams(imgHeight, imgHeight));

        viewHolder.setText(R.id.tv_title, data.getName());
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
