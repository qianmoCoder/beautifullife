package com.ddu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzbzz on 2017/5/5.
 */

public class AnimaButton extends View {

    private Rect rect = new Rect();
    private Paint paint = new Paint();

    public AnimaButton(Context context) {
        this(context, null);
    }

    public AnimaButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AnimaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRoundRect(rect,20,20,);
    }
}
