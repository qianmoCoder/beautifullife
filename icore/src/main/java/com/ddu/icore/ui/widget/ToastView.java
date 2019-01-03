package com.ddu.icore.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;

/**
 * Created by yzbzz on 2018/8/21.
 */
public class ToastView extends FrameLayout {

    private Context mContext;

    private LinearLayout llToast;
    private TextView tvToast;
    private ImageView ivToast;

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

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

        paddingLeft = paddingRight = getResources().getDimensionPixelSize(R.dimen.dp_20);
        paddingTop = paddingBottom = getResources().getDimensionPixelSize(R.dimen.dp_15);

        LayoutInflater.from(context).inflate(R.layout.toast_layout, this);

        llToast = findViewById(R.id.ll_toast);
        tvToast = findViewById(R.id.tv_toast);
        ivToast = findViewById(R.id.iv_toast);

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
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

    public void setIcon(Drawable drawable) {
        if (null == drawable) {
            ivToast.setVisibility(GONE);
        } else {
            ivToast.setVisibility(VISIBLE);
            ivToast.setImageDrawable(drawable);
        }

    }

    public void setIcon(@DrawableRes int resId) {
        if (resId == -1) {
            ivToast.setVisibility(GONE);
        } else {
            ivToast.setVisibility(VISIBLE);
            ivToast.setImageResource(resId);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        llToast.setPadding(left, top, right, bottom);
    }
}
