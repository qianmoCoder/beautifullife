package com.ddu.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuhongzhe on 16/6/6.
 */
public class CustomView extends View {

    private int cx = 100;
    private int cy = 100;

    @NonNull
    private final Paint paint;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);

        paint.setColor(Color.BLUE);
        canvas.drawCircle(cx, cy, 50, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawCircle(cx, cy * 2 + 20, 50, paint);


        paint.setColor(Color.GREEN);
        Rect rect = new Rect(cx + 70, cy + 70, cx + 70 + 100, cy + 70 + 100);
        canvas.drawRect(rect, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx + 70, cx - 50, cx + 70 + 100, cx - 50 + 100, paint);

    }
}
