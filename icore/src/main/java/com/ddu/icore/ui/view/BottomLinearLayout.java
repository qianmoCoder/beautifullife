package com.ddu.icore.ui.view;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by admin on 2015/11/28.
 */
public class BottomLinearLayout extends LinearLayout {

    private int maxHeight;

    public BottomLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public BottomLinearLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        maxHeight = (int) (0.6 * getResources().getDisplayMetrics().heightPixels);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getSize(heightMeasureSpec) > maxHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
