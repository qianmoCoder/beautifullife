package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.util.sys.ViewUtils;


public class OptionItemView extends RelativeLayout {

    private Context mContext;

    private TextView tvLeftText;
    private TextView tvRightText;
    private ImageView ivIcon;

    private ImageView ivArrow;


    public OptionItemView(Context context) {
        this(context, null);
    }

    public OptionItemView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        init();

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView);

        int resId = a.getResourceId(R.styleable.CustomItemView_image, -1);
        if (resId > 0) {
            ivIcon.setImageResource(resId);
        } else {
            ivIcon.setVisibility(GONE);
        }

        int mImageScale = a.getInt(R.styleable.CustomItemView_imageScaleType, -1);
        if (mImageScale > 0) {
            ivIcon.setScaleType(sScaleTypeArray[mImageScale]);
        }

        String title = a.getString(R.styleable.CustomItemView_leftText);
        if (!TextUtils.isEmpty(title)) {
            tvLeftText.setText(title);
        } else {
            tvLeftText.setVisibility(GONE);
        }

        int color = a.getColor(R.styleable.CustomItemView_leftTextColor, Color.BLACK);
        tvLeftText.setTextColor(color);

        int size = a.getDimensionPixelSize(R.styleable.CustomItemView_leftTextSize, 16);
        tvLeftText.setTextSize(size);

        String content = a.getString(R.styleable.CustomItemView_rightText);
        if (!TextUtils.isEmpty(content)) {
            tvRightText.setText(content);
        } else {
            tvRightText.setVisibility(GONE);
        }

        int contentColor = a.getColor(R.styleable.CustomItemView_rightTextColor, 0xff666666);
        tvRightText.setTextColor(contentColor);

        int contentSize = a.getDimensionPixelSize(R.styleable.CustomItemView_rightTextSize, 14);
        tvRightText.setTextSize(contentSize);

        int arrowResId = a.getResourceId(R.styleable.CustomItemView_carrowImage, -1);
        if (arrowResId > 0) {
            ivArrow.setImageResource(arrowResId);
        } else {
            ivArrow.setVisibility(GONE);
        }

        int arrowScale = a.getInt(R.styleable.CustomItemView_carrowImageScaleType, -1);
        if (arrowScale > 0) {
            ivArrow.setScaleType(sScaleTypeArray[arrowScale]);
        }

        a.recycle();

    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.option_item_view, this);
        ivIcon = ViewUtils.findViewById(this, R.id.iv_icon);
        tvLeftText = ViewUtils.findViewById(this, R.id.tv_left_text);
        tvRightText = ViewUtils.findViewById(this, R.id.tv_right_text);
        ivArrow = ViewUtils.findViewById(this, R.id.iv_arrow);
    }


    public void setLeftText(String str) {
        tvLeftText.setText(str);
    }

    public void setLeftText(int id) {
        tvLeftText.setText(id);
    }

    public void setIcon(int id) {
        ivIcon.setVisibility(VISIBLE);
        ivIcon.setImageResource(id);
    }

    public void setRightText(String content) {
        tvRightText.setText(content);
        tvRightText.setVisibility(VISIBLE);
    }

    public void setArrowVisibility(int visibility) {
        ivArrow.setVisibility(visibility);
    }

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };
}
