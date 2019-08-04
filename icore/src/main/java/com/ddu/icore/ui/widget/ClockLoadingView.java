package com.ddu.icore.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.ddu.icore.R;


public class ClockLoadingView extends View {

    private final static float MIN_PIN_MAX_ANGLE = (float) (2 * Math.PI);
    private final static float HOUR_PIN_ANGLE_INTERVAL = (float) (2 * Math.PI / 12);
    private final static int COLOR_BG = R.color.loading_clock_bg;
    private final static int COLOR_PIN = R.color.loading_clock_pin;
    private float borderCircleRadius;
    private Path borderCirclePath;
    private Path insideCirclePath;
    private Paint borderCirclePaint;
    private Paint insideCirclePaint;
    private Paint hourHandPaint;
    private Paint minHandPaint;
    private ValueAnimator valueAnimator;
    private long animatorPlayTime;
    private float centerX;
    private float centerY;
    private float currentMinHandAngle = 0;
    private float currentHourHandAngle = 0;

    public ClockLoadingView(Context context) {
        this(context, null);
    }

    public ClockLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        borderCirclePath = new Path();
        insideCirclePath = new Path();

        borderCirclePaint = new Paint();
        borderCirclePaint.setAntiAlias(true);
        borderCirclePaint.setColor(ContextCompat.getColor(context, COLOR_BG));
        borderCirclePaint.setStyle(Paint.Style.STROKE);

        insideCirclePaint = new Paint();
        insideCirclePaint.setAntiAlias(true);
        insideCirclePaint.setColor(ContextCompat.getColor(context, COLOR_BG));
        insideCirclePaint.setStyle(Paint.Style.FILL);

        hourHandPaint = new Paint();
        hourHandPaint.setAntiAlias(true);
        hourHandPaint.setStrokeCap(Paint.Cap.ROUND);
        hourHandPaint.setColor(ContextCompat.getColor(context, COLOR_PIN));

        minHandPaint = new Paint();
        minHandPaint.setAntiAlias(true);
        minHandPaint.setStrokeCap(Paint.Cap.ROUND);
        minHandPaint.setColor(ContextCompat.getColor(context, COLOR_PIN));
        setRotation(-90);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth == MeasureSpec.AT_MOST && modeHeight == MeasureSpec.AT_MOST) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        stop();
        int minSize = Math.min(w, h);
        //外边框的线的宽度
        float borderCircleStrokeWidth = minSize / 16f;
        //指针的线的宽度
        float pinStrokeWidth = minSize / 10f;

        borderCirclePaint.setStrokeWidth(borderCircleStrokeWidth);
        hourHandPaint.setStrokeWidth(pinStrokeWidth);
        minHandPaint.setStrokeWidth(pinStrokeWidth);
        //外圆半径
        borderCircleRadius = minSize / 2 - borderCircleStrokeWidth;

        centerX = w / 2;
        centerY = h / 2;

        borderCirclePath.reset();
        borderCirclePath.addCircle(w / 2, h / 2, borderCircleRadius, Path.Direction.CW);
        insideCirclePath.addCircle(w / 2, h / 2, borderCircleRadius - pinStrokeWidth, Path.Direction.CW);
        setupAnimator();
    }

    /**
     * 目前分针1s一圈，时针12s一圈
     */
    private void setupAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, MIN_PIN_MAX_ANGLE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentMinHandAngle = (float) animation.getAnimatedValue();
                if (currentHourHandAngle > 2 * Math.PI) {
                    currentHourHandAngle = 0;
                } else {
                    currentHourHandAngle = currentHourHandAngle + (float) (Math.PI / (60 * 6));
                }
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(borderCirclePath, borderCirclePaint);
        canvas.drawPath(insideCirclePath, insideCirclePaint);
        float minEndX = (float) ((borderCircleRadius * 0.5) * Math.cos(currentMinHandAngle) + centerX);
        float minEndY = (float) ((borderCircleRadius * 0.5) * Math.sin(currentMinHandAngle) + centerY);

        float hourEndX = (float) ((borderCircleRadius * 0.5) * Math.cos(currentHourHandAngle) + centerX);
        float hourEndY = (float) ((borderCircleRadius * 0.5) * Math.sin(currentHourHandAngle) + centerY);

        canvas.drawLine(centerX, centerY, hourEndX, hourEndY, hourHandPaint);
        canvas.drawLine(centerX, centerY, minEndX, minEndY, minHandPaint);
    }

    public void start() {
        if (valueAnimator != null && !valueAnimator.isRunning()) {
            //valueAnimator.setCurrentPlayTime(animatorPlayTime);
            //每次都从头执行
            currentMinHandAngle = 0;
            currentHourHandAngle = 0;
            valueAnimator.start();
        }
    }

    public void stop() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            //animatorPlayTime = valueAnimator.getCurrentPlayTime();
            valueAnimator.cancel();
        }
    }
}