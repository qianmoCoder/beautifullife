package com.ddu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ddu.R;

/**
 * Created by liuhongzhe on 16/6/6.
 */
public class DrawView3 extends View {

    private static final int TOTAL_COUNT = 8;
    private static final int LIGHT_COUNT = 0;

    private float DEFAULT_COLUMN_WIDTH = getResources().getDimensionPixelSize(R.dimen.dp_8);
    private float DEFAULT_COLUMN_INIT_HEIGHT = getResources().getDimensionPixelSize(R.dimen.dp_18);
    private float DEFAULT_COLUMN_INCREASE_HEIGHT = getResources().getDimensionPixelSize(R.dimen.dp_10);
    private float DEFAULT_COLUMN_SPACE = getResources().getDimensionPixelSize(R.dimen.dp_10);

    private float DEFAULT_RX = getResources().getDimensionPixelSize(R.dimen.dp_10);
    private float DEFAULT_RY = getResources().getDimensionPixelSize(R.dimen.dp_10);

    private int COLOR_LIGHT = getResources().getColor(R.color.c_f5b227);
    private int COLOR_DARK = getResources().getColor(R.color.c_006581);

    protected Paint mLightPaint = new Paint();
    protected Paint mDarkPaint = new Paint();
    private RectF mRectF;

    private int mHeight;

    private int mTotalSize = TOTAL_COUNT;
    private int mLightSize = LIGHT_COUNT;

    private int mColorLight = COLOR_LIGHT;
    private int mColorDark = COLOR_DARK;

    private float mColumnWidth = DEFAULT_COLUMN_WIDTH;
    private float mColumnInitHeight = DEFAULT_COLUMN_INIT_HEIGHT;
    private float mColumnIncreaseHeight = DEFAULT_COLUMN_INCREASE_HEIGHT;
    private float mColumnSpace = DEFAULT_COLUMN_SPACE;

    private float mRx = DEFAULT_RX;
    private float mRy = DEFAULT_RY;


    public DrawView3(Context context) {
        this(context, null);
    }

    public DrawView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView3(Context context, AttributeSet attrs,
                     int defStyle) {
        super(context, attrs, defStyle);
        obtainStyledAttributes(attrs);
        init();
    }

    private void init() {
        mLightPaint.setAntiAlias(true);
        mLightPaint.setDither(true);
        mLightPaint.setColor(mColorLight);
        mLightPaint.setStyle(Paint.Style.FILL);

        mDarkPaint.setAntiAlias(true);
        mDarkPaint.setDither(true);
        mDarkPaint.setColor(mColorDark);
        mDarkPaint.setStyle(Paint.Style.FILL);

        mRectF = new RectF();
    }


    private void obtainStyledAttributes(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DrawView3);

        mTotalSize = a.getInt(R.styleable.DrawView3_column_total_count, TOTAL_COUNT);
        mLightSize = a.getInt(R.styleable.DrawView3_column_light_count, LIGHT_COUNT);

        mColorLight = a.getColor(R.styleable.DrawView3_column_color_light, COLOR_LIGHT);
        mColorDark = a.getColor(R.styleable.DrawView3_column_color_dark, COLOR_DARK);

        mColumnWidth = a.getDimension(R.styleable.DrawView3_column_width, DEFAULT_COLUMN_WIDTH);
        mColumnInitHeight = a.getDimension(R.styleable.DrawView3_column_init_height, DEFAULT_COLUMN_INIT_HEIGHT);
        mColumnIncreaseHeight = a.getDimension(R.styleable.DrawView3_column_increase_height, DEFAULT_COLUMN_INCREASE_HEIGHT);
        mColumnSpace = a.getDimension(R.styleable.DrawView3_column_space, DEFAULT_COLUMN_SPACE);

        mRx = a.getDimension(R.styleable.DrawView3_column_rx, DEFAULT_RX);
        mRy = a.getDimension(R.styleable.DrawView3_column_ry, DEFAULT_RY);

        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        mHeight = h;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int spaceCount = mTotalSize - 1;
        int paintHeight = (int) (mColumnInitHeight + spaceCount * mColumnIncreaseHeight);
        int paintWidth = (int) (mTotalSize * mColumnWidth + spaceCount * mColumnSpace);

        if (heightMode != MeasureSpec.EXACTLY) {
            int exceptHeight = (getPaddingTop() + getPaddingBottom() + paintHeight);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
                    MeasureSpec.EXACTLY);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            int exceptWidth = (getPaddingLeft() + getPaddingRight() + paintWidth);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mBottom = mHeight;
        for (int i = 0; i < mTotalSize; i++) {
            float mLeft = i * (mColumnWidth + mColumnSpace);
            float mRight = mLeft + mColumnWidth;
            float mTop = mHeight - mColumnInitHeight - i * mColumnIncreaseHeight;
            mRectF.set(mLeft, mTop, mRight, mBottom);

            if (i < mLightSize) {
                canvas.drawRoundRect(mRectF, mRx, mRy, mLightPaint);
            } else {
                canvas.drawRoundRect(mRectF, mRx, mRy, mDarkPaint);
            }
        }
    }

}
