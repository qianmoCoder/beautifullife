package com.ddu.icore.ui.help;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import com.ddu.icore.R;

/**
 * Created by yzbzz on 2017/5/2.
 */

public class ShapeLayoutInject {

    private View mView;

    private GradientDrawable mBackground;
    //radius
    private float mRadius = 0;

    private int mGradientShapeType = GradientDrawable.RECTANGLE;

    //stroke
    private float mStrokeDashWidth = 0;
    private float mStrokeDashGap = 0;

    private int mStrokeWidth = 0;

    private int mStrokeColor = 0;

    //background color
    @ColorInt
    private int mBackgroundColor = Color.TRANSPARENT;


    private ShapeLayoutInject(View view, AttributeSet attrs) {
        mView = view;
        parseAttributeSet(attrs);
    }

    public static ShapeLayoutInject inject(View view, AttributeSet attrs) {
        return new ShapeLayoutInject(view, attrs);
    }

    private void parseAttributeSet(AttributeSet attrs) {
        mBackground = new GradientDrawable();
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.ShapeLayoutInject);

        // backgroundColor
        mBackgroundColor = a.getColor(R.styleable.ShapeLayoutInject_l_background, Color.TRANSPARENT);
        // radius
        mRadius = a.getDimensionPixelSize(R.styleable.ShapeLayoutInject_l_radius, 0);

        // stroke
        mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.ShapeLayoutInject_l_strokeDashWidth, 0);
        mStrokeDashGap = a.getDimensionPixelSize(R.styleable.ShapeLayoutInject_l_strokeDashGap, 0);

        mStrokeWidth = a.getDimensionPixelSize(R.styleable.ShapeLayoutInject_l_strokeWidth, 0);
        mStrokeColor = a.getColor(R.styleable.ShapeLayoutInject_l_strokeColor, 0);

        a.recycle();

        background();
    }

    public void background() {
        if (null != mView) {
            background(mBackground, mBackgroundColor, mStrokeWidth, mStrokeColor);
            mView.post(new Runnable() {
                @Override
                public void run() {
                    mView.setBackground(mBackground);
                }
            });
        }
    }


    private void background(GradientDrawable gradientDrawable, int color, int strokeWidth, int strokeColor) {
        if (null != gradientDrawable) {
            gradientDrawable.setShape(mGradientShapeType);
            gradientDrawable.setColor(color);
            gradientDrawable.setCornerRadius(mRadius);
            gradientDrawable.setStroke(strokeWidth, strokeColor, mStrokeDashWidth, mStrokeDashGap);
        }
    }
}
