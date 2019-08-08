package com.ddu.icore.ui.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by yzbzz on 2019-08-08.
 */
public class MoveButton extends AppCompatButton {

    public MoveButton(Context context) {
        super(context);
    }

    public MoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }
}
