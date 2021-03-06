package com.ddu.icore.help;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * Created by yzbzz on 2018/12/19.
 */
public class CustomLinkMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        boolean b = super.onTouchEvent(widget,buffer,event);

        if(!b && event.getAction() == MotionEvent.ACTION_UP){
            ViewParent parent = widget.getParent();
            if (parent instanceof ViewGroup) {
                return ((ViewGroup) parent).performClick();
            }
        }
        return b;
    }

    public static CustomLinkMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new CustomLinkMovementMethod();

        return sInstance;
    }


    private static CustomLinkMovementMethod sInstance;
}
