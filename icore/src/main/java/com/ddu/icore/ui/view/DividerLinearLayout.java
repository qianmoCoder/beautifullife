package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ddu.icore.R;


/**
 * Created by admin on 2015/11/28.
 */
public class DividerLinearLayout extends LinearLayout {

    private final Rect mTempRect = new Rect();

    private int mDividerHeight;

    @Nullable
    private Drawable mDivider;

    private int marginLeft;

    private int marginRight;

    public DividerLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public DividerLinearLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dividerLinearLayout);
        final Drawable d = a.getDrawable(R.styleable.dividerLinearLayout_divider);
        marginLeft = a.getDimensionPixelSize(R.styleable.dividerLinearLayout_divider_margin_left, 0);
        marginRight = a.getDimensionPixelSize(R.styleable.dividerLinearLayout_divider_margin_right, 0);
        mDividerHeight = a.getDimensionPixelSize(R.styleable.dividerLinearLayout_divider_height, 0);
        if (d != null) {
            setDivider(d);
        }
        a.recycle();
    }


    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        final int dividerHeight = mDividerHeight;
        final boolean drawDividers = dividerHeight > 0 && mDivider != null;
        if (drawDividers) {
            final Rect bounds = mTempRect;
            int bottom;
            int count = getChildCount();
            int right = getRight();
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
                bottom = child.getBottom();
                bounds.top = bottom;
                bounds.bottom = bottom + dividerHeight;
                bounds.left = marginLeft;
                bounds.right = right - marginRight;
                drawDivider(mDivider, canvas, bounds);
            }
        }
        super.dispatchDraw(canvas);
    }

    public void setDivider(@Nullable Drawable divider) {
        if (divider != null) {
            int height = divider.getIntrinsicHeight();
            if (height > 0) {
                mDividerHeight = height;
            }
        } else {
            mDividerHeight = 0;
        }
        mDivider = divider;
        requestLayout();
        invalidate();
    }

    void drawDivider(@NonNull Drawable divider, @NonNull Canvas canvas, @NonNull Rect bounds) {
        divider.setBounds(bounds);
        divider.draw(canvas);
    }

}
