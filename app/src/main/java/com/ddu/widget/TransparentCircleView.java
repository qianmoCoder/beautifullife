package com.ddu.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/***
 * 透明圆形背景
 */
public class TransparentCircleView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private float rx = 0;
    private int w, h;

    private OnAnimListener l;
    private ValueAnimator anim;

    public TransparentCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mPaint.setAlpha(0x0);
        mPaint.setColor(0xFF000000);

    }

    private void setPath() {
        if (w == 0 || h == 0) {
            return;
        }

        float hh = (h - 2 * rx) / 2;


        mPath.reset();
        mPath.lineTo(0, h);
        mPath.lineTo(w, h);
        mPath.lineTo(w, 0);
        mPath.lineTo(0, 0);

//        mPath.lineTo(w/2,hh);
        mPath.addCircle(w / 2, h / 2, rx, Path.Direction.CW);
//        mPath.moveTo(0,0);
//        mPath.lineTo(w/2,0);
//        mPath.lineTo(w/2,hh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

//        if(widthMode==MeasureSpec.EXACTLY){
        w = width;
//        }
//        if(heightMode==MeasureSpec.EXACTLY){
        h = height;
//        }
        rx = Math.max(w, h) / 2;

//        Log.i("xx","w = "+w+" h = "+h);


    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        Log.i("xx","onLayout");
//
//    }

    public void startAnim() {
//        float r=(float) Math.sqrt(w*w+h*h)/2;
        float w1, w2;
        if (w < h) {
            w1 = h;
            w2 = w;
        } else {
            w1 = w;
            w2 = h;
        }
        anim = ValueAnimator.ofFloat(w1, w2 / 2);
        anim.setDuration(300);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(1);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                rx = (Float) valueAnimator.getAnimatedValue();
                Log.i("lhz", "currentValue = " + rx);
                setPath();
                invalidate();
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i("xx", "end");
                if (l != null) {
                    l.onComplete();
                }
//                    startAnim(true,false);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.i("xx", "repeat");
                if (l != null) {
                    l.onHalf();
                }

            }
        });
        anim.start();
    }

    public void cancelAnim() {
        anim.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPath();
        canvas.drawPath(mPath, mPaint);

//        canvas.drawCircle(100,100,100,mPaint);
    }

    public interface OnAnimListener {
        void onHalf();

        void onComplete();

    }

    public void setOnAnimListener(OnAnimListener l) {

        this.l = l;
    }
}
