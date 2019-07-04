package com.ddu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

/**
 * Created by yzbzz on 2017/5/8.
 */

public class CustomerR extends LinearLayout {


    public CustomerR(Context context) {
        super(context);
    }

    public CustomerR(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerR(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomerR(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v("lhz","CustomerR onMeasure");
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        Log.v("lhz","CustomerR measureChild");
    }

}
