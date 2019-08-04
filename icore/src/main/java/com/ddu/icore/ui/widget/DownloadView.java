package com.ddu.icore.ui.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class DownloadView extends View {

    //默认的环的宽度
    private static final float STROKE_WIDTH_DEFAULT = 5;
    //默认的半径
    private static final float RADIUS_DEFAULT = 50;
    //默认最大的进度
    private static final int MAX_PROGRESS_DEFAULT = 100;
    private Paint paint;
    private float mStrokeWidth = STROKE_WIDTH_DEFAULT;
    private float mRadius = RADIUS_DEFAULT;
    private int mProgress;
    private int mMaxProgress = MAX_PROGRESS_DEFAULT;
    private int mBgColor;

    private final RectF mBackgroundRectF = new RectF();
    private final RectF mProgressRectF = new RectF();
    private float mCenterX;
    private float mCenterY;
    private PorterDuffXfermode porterDuffXfermode;

    public DownloadView(Context context) {
        this(context, null);
    }

    public DownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景
        paint.setColor(mBgColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mCenterX, mCenterY, Math.min(mCenterX, mCenterY), paint);
        // 绘制圆环
        paint.setStrokeWidth(mStrokeWidth);
        // 采用 clear 的方式
        paint.setXfermode(porterDuffXfermode);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mCenterX, mCenterY, mRadius, paint);
        // 绘制内圆
        paint.setStyle(Paint.Style.FILL);
        float sweepAngle = 360f * mProgress / mMaxProgress;
        canvas.drawArc(mProgressRectF, -90, sweepAngle, true, paint);
        // 记得设置为 null 不然会没有效果
        paint.setXfermode(null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        float min = Math.min(mCenterX, mCenterY);

        mBackgroundRectF.left = mCenterX - min;
        mBackgroundRectF.top = mCenterY - min;
        mBackgroundRectF.right = mCenterX + min;
        mBackgroundRectF.bottom = mCenterY + min;

        mProgressRectF.left = mCenterX - mRadius;
        mProgressRectF.top = mCenterY - mRadius;
        mProgressRectF.right = mCenterX + mRadius;
        mProgressRectF.bottom = mCenterY + mRadius;
    }

    /**
     * @param progress 设置进度
     */
    public void setProgress(int progress) {
        if (progress >= 0 && progress <= mMaxProgress) {
            this.mProgress = progress;
            postInvalidate();
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    /**
     * @param strokeWidth 设置圆环的宽度
     */
    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    /**
     * @param radius 设置圆的的半径
     */
    public void setRadius(float radius) {
        this.mRadius = radius;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
    }
}
