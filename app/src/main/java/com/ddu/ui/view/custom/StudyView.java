package com.ddu.ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yzbzz on 2019-07-16.
 */
public class StudyView extends View {

    public StudyView(Context context) {
        super(context);
    }

    public StudyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StudyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StudyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v("lhz", "dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    int lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("lhz", "onTouchEvent: " + event.getAction());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //将点下的点的坐标保存
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - lastX));
                setTranslationY(getY() + (event.getY() - lastY));
                break;
        }
        return true;
    }

}
