package com.ddu.icore.ui.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddu.icore.R;

/**
 * Created by yzbzz on 2018/8/21.
 */
public class ToastView extends FrameLayout {

    private Context mContext;

    private TextView tvToast;
    private ImageView ivToast;

    public ToastView(@NonNull Context context) {
        this(context, null);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.toast_layout, this);
        tvToast = findViewById(R.id.tv_toast);
        ivToast = findViewById(R.id.iv_toast);
    }

    public void setText(@NonNull CharSequence msg) {
        tvToast.setText(msg);
    }

    public void setText(@StringRes int resId) {
        tvToast.setText(resId);
    }

    public void setTextColor(@ColorInt int resId) {
        tvToast.setTextColor(resId);
    }

    public void setTextSize(int size) {
        tvToast.setTextSize(size);
    }


}
