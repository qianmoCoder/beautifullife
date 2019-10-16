package com.ddu.icore.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.callback.InConsumer2;
import com.ddu.icore.entity.BottomItem;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class BottomAdapter extends DefaultRVAdapter<BottomItem> {

    private int mHeight;
    private int mSmallHeight;

    private int mImgHeight;
    private int mSmallImgHeight;

    private int mImgWidth;
    private int mSmallImgWidth;

    private BottomDialogParams mParams;

    private InConsumer2<BottomItem, Integer> mConsumer2;

    public BottomAdapter(Context context, @NonNull BottomDialogParams params) {
        super(context, params.mItems);

        mParams = params;

        mHeight = (int) mContext.getResources().getDimension(R.dimen.dp_120);
        mImgHeight = mImgWidth = (int) mContext.getResources().getDimension(R.dimen.dp_40);

        mSmallHeight = (int) mContext.getResources().getDimension(R.dimen.dp_90);
        mSmallImgHeight = mSmallImgWidth = (int) mContext.getResources().getDimension(R.dimen.dp_30);

        int size = params.mItems.size();
        int count = mParams.mSpanCount;

        if (size <= count) {
            mParams.mItemHeight = mHeight;
            mParams.mIconHeight = mImgHeight;
            mParams.mIconWidth = mImgWidth;
        } else {
            mParams.mItemHeight = mSmallHeight;
            mParams.mIconHeight = mSmallImgHeight;
            mParams.mIconWidth = mSmallImgWidth;
        }

    }

    @Override
    public int getLayoutId(int viewType) {
        int resId = R.layout.i_rv_item_linear;
        if (mParams.mLayoutType == BottomDialogParams.TYPE_GRID) {
            resId = R.layout.i_rv_item_grid;
        }
        return resId;
    }

    @Override
    public void bindView(ViewHolder viewHolder, final BottomItem data, final int position) {
        TextView tvTitle = viewHolder.getView(R.id.tv_title);
        tvTitle.setText(data.getTitle());

        ImageView ivIcon = viewHolder.getView(R.id.iv_left_icon);
        Drawable drawable = data.getIcon();
        if (null != drawable) {
            ivIcon.setVisibility(View.VISIBLE);
            ivIcon.setImageDrawable(data.getIcon());
        } else {
            ivIcon.setVisibility(View.GONE);
        }
        if (mParams.mLayoutType == BottomDialogParams.TYPE_GRID) {

            if (mParams.mIconWidth > 0 && mParams.mIconHeight > 0) {
                ivIcon.setLayoutParams(new LinearLayout.LayoutParams(mParams.mIconWidth, mParams.mIconHeight));
            }

            if (mParams.mItemHeight > 0) {
                viewHolder.itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mParams.mItemHeight));
            }
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mConsumer2) {
                    mConsumer2.accept(data, position);
                }
            }
        });
    }

    public void setConsumer2(InConsumer2<BottomItem, Integer> consumer2) {
        this.mConsumer2 = consumer2;
    }
}
