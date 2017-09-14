package com.ddu.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzbzz on 2017/9/1.
 */

public class CustomerUI extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    {
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225,false);
        path.lineTo(400, 542);
    }

    public CustomerUI(Context context) {
        super(context);
    }

    public CustomerUI(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerUI(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.RED);
        canvas.drawPoint(200, 200, paint);
        canvas.drawPoint(300, 300, paint);
        canvas.drawPoint(400, 400, paint);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);
    }
}
