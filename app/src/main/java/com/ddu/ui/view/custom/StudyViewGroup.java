package com.ddu.ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yzbzz on 2019-07-16.
 */
public class StudyViewGroup extends LinearLayout {

    public StudyViewGroup(Context context) {
        super(context);
    }

    public StudyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StudyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StudyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

//    @Override
//    public boolean performClick() {
//        return super.performClick();
//    }
}
