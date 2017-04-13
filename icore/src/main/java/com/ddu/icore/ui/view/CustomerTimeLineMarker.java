package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.ddu.icore.R;

/**
 * Created by yzbzz on 2017/4/12.
 */

public class CustomerTimeLineMarker extends View {

    private int mTopOffset = 50;
    private int mSpaceOffset = 150;

    private int mMarkerSize = 8;
    private int mLineSize = 2;

    private Drawable mLine;
    private Drawable mMarkerDrawable;

    private int mCenterX;
    private int mCenterY;

    private int mWidth;
    private int mHeight;

    private int count = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLine != null) {
            mLine.draw(canvas);
        }

        if (mMarkerDrawable != null) {
            for (int i = 0; i < count; i++) {
                int markerSize = Math.min(mMarkerSize, Math.min(mWidth, mHeight));
                mMarkerDrawable.setBounds(mCenterX - markerSize / 2, mTopOffset + i * mSpaceOffset - markerSize / 2,
                        mCenterX + markerSize / 2, 30 + i * 100 + markerSize / 2);
                mMarkerDrawable.draw(canvas);
            }
        }

        super.onDraw(canvas);
    }

    public CustomerTimeLineMarker(Context context) {
        this(context, null);
    }

    public CustomerTimeLineMarker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerTimeLineMarker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TimeLineMarker);

        mMarkerSize = a.getDimensionPixelSize(R.styleable.TimeLineMarker_markerSize, mMarkerSize);
        mLineSize = a.getDimensionPixelSize(R.styleable.TimeLineMarker_lineSize, mLineSize);

        mLine = a.getDrawable(R.styleable.TimeLineMarker_beginLine);

        mTopOffset = a.getDimensionPixelSize(R.styleable.TimeLineMarker_lineSize, mTopOffset);
        mSpaceOffset = a.getDimensionPixelSize(R.styleable.TimeLineMarker_lineSize, mSpaceOffset);

        mMarkerDrawable = a.getDrawable(
                R.styleable.TimeLineMarker_marker);

        a.recycle();

        if (mLine != null) {
            mLine.setCallback(this);
        }

        if (mMarkerDrawable != null) {
            mMarkerDrawable.setCallback(this);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getPaddingLeft() + getPaddingRight();
        int h = getPaddingTop() + getPaddingBottom();

        if (mMarkerDrawable != null) {
            w += mMarkerSize;
            h += mMarkerSize;
        }

        w = Math.max(w, getMeasuredWidth());
        h = Math.max(h, getMeasuredHeight());

        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initDrawableSize();
    }

    private void initDrawableSize() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();

        int width = getWidth();
        int height = getHeight();

        int cWidth = width - pLeft - pRight;
        int cHeight = height - pTop - pBottom;

        Rect bounds;

        int centerX = width / 2;
        int centerY = height / 2;
        mCenterX = centerX;
        mCenterY = centerY;

        mWidth = cWidth;
        mHeight = cHeight;

        if (mMarkerDrawable != null) {
            // Size
            int markerSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));
            mMarkerDrawable.setBounds(centerX - markerSize / 2, centerY - markerSize / 2,
                    centerX + markerSize / 2, centerY + markerSize / 2);

            bounds = mMarkerDrawable.getBounds();
        } else {
            bounds = new Rect(centerX, centerY, centerX, centerY);
        }

        int halfLineSize = mLineSize >> 1;
        int lineLeft = bounds.centerX() - halfLineSize;

        if (mLine != null) {
            mLine.setBounds(lineLeft, 0, lineLeft + mLineSize, bounds.top);
        }

    }

    public void setLineSize(int lineSize) {
        if (mLineSize != lineSize) {
            this.mLineSize = lineSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setMarkerSize(int markerSize) {
        if (this.mMarkerSize != markerSize) {
            mMarkerSize = markerSize;
            initDrawableSize();
            invalidate();
        }
    }

    public void setBeginLine(Drawable beginLine) {
        if (this.mLine != beginLine) {
            this.mLine = beginLine;
            if (mLine != null) {
                mLine.setCallback(this);
            }
            initDrawableSize();
            invalidate();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        initDrawableSize();
        invalidate();
    }
}
