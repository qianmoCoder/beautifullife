package com.ddu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.util.sys.DensityUtils;

/**
 * Created by yzbzz on 2017/12/1.
 */

public class LoweImageView extends View {

    private float mRatio;


    public LoweImageView(Context context) {
        this(context, null);
    }

    public LoweImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoweImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.LoweImageView);
        float defaultRatio = DensityUtils.getScreenRate(context);
        mRatio = typedArray.getFloat(R.styleable.LoweImageView_ratio, defaultRatio);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 宽模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 宽大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 高大小
        int heightSize;
        // 只有宽的值是精确的才对高做精确的比例校对
//        if (widthMode == MeasureSpec.EXACTLY && mRatio > 0) {
            heightSize = (int) (widthSize * mRatio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                    MeasureSpec.EXACTLY);
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
