package com.ddu.icore.ui.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.ui.help.ITitleBar;
import com.ddu.icore.util.sys.ViewUtils;

/**
 * Created by yzbzz on 2017/1/23.
 */

public class TitleBar extends LinearLayout implements ITitleBar {

    private LinearLayout mTitleBarLeft;
    private LinearLayout mTitleBarMiddle;
    private LinearLayout mTitleBarRight;

    private ImageView mLeftImg;
    private TextView mLeftText;
    private TextView mMiddleText;
    private TextView mRightText;
    private ImageView mRightImg;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mTitleBarLeft = ViewUtils.findViewById(this, R.id.ll_title_bar_left);
        mTitleBarMiddle = ViewUtils.findViewById(this, R.id.ll_title_bar_middle);
        mTitleBarRight = ViewUtils.findViewById(this, R.id.ll_title_bar_right);

        mLeftImg = ViewUtils.findViewById(this, R.id.iv_title_bar_left);
        mLeftText = ViewUtils.findViewById(this, R.id.tv_title_bar_left);
        mMiddleText = ViewUtils.findViewById(this, R.id.tv_title_bar_title);
        mRightText = ViewUtils.findViewById(this, R.id.tv_title_bar_right);
        mRightImg = ViewUtils.findViewById(this, R.id.iv_title_bar_right);
    }

    public void setDefaultTitle(int resId, OnClickListener onClickListener) {
        setDefaultLeftImg(onClickListener);
        setMiddleText(resId);
    }

    public void setDefaultTitle(String title, OnClickListener onClickListener) {
        setDefaultLeftImg(onClickListener);
        setMiddleText(title);
    }

    public void setDefaultLeftImg(OnClickListener onClickListener) {
        setLeftImg(R.drawable.titlebar_back_icon);
        setLeftOnClickListener(onClickListener);
    }

    public void setLeftOnClickListener(OnClickListener onClickListener) {
        if (null != mTitleBarLeft) {
            mTitleBarLeft.setOnClickListener(onClickListener);
        }
    }


    public void setLeftImg(@DrawableRes int resId) {
        if (null != mLeftImg) {
            mLeftImg.setImageResource(resId);
        }
    }

    public void setMiddleText(@StringRes int resId) {
        if (null != mMiddleText) {
            mMiddleText.setText(resId);
        }
    }

    public void setMiddleText(CharSequence character) {
        if (null != mMiddleText) {
            mMiddleText.setText(character);
        }
    }

    public void setMiddleText(String text) {
        if (null != mMiddleText) {
            mMiddleText.setText(text);
        }
    }

    public void setRightText(String text, OnClickListener onClickListener) {
        if (null != mRightText) {
            mTitleBarRight.setVisibility(VISIBLE);
            mRightText.setVisibility(VISIBLE);
            mRightText.setText(text);
            mRightText.setOnClickListener(onClickListener);
        }
    }

    public void setRightImg(int resId, OnClickListener onClickListener) {
        if (null != mRightImg) {
            mTitleBarRight.setVisibility(VISIBLE);
            mRightImg.setVisibility(VISIBLE);
            mRightImg.setImageResource(resId);
            mRightImg.setOnClickListener(onClickListener);
        }
    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public LinearLayout getTitleBarLeft() {
        return mTitleBarLeft;
    }

    @Override
    public LinearLayout getTitleBarMiddle() {
        return mTitleBarMiddle;
    }

    @Override
    public LinearLayout getTitleBarRight() {
        return mTitleBarRight;
    }

    @Override
    public ImageView getLeftImg() {
        return mLeftImg;
    }

    @Override
    public TextView getLeftText() {
        return mLeftText;
    }

    @Override
    public TextView getMiddleText() {
        return mMiddleText;
    }

    @Override
    public TextView getRightText() {
        return mRightText;
    }

    public ImageView getRightImg() {
        return mRightImg;
    }
}
