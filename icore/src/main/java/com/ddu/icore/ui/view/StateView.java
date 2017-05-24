package com.ddu.icore.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzbzz on 2017/4/21.
 */

public class StateView extends View {

    private int strokeWidth = 3;
    private int radius = 20;

    private ShapeDrawable drawable;

    public StateView(Context context) {
        super(context);
        init();
    }

    public StateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RectF inset = new RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
        float[] innerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        drawable = new ShapeDrawable(new RoundRectShape(outerR, inset, innerR));
        drawable.getPaint().setColor(0xFF0000FF);
//        drawable.getPaint().setStyle(Paint.Style.STROKE);
//        drawable.getPaint().setStrokeWidth(3);
        drawable.getPaint().setDither(true);
        drawable.getPaint().setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawable.setBounds(getLeft(), getTop(), getWidth(), getHeight());
        drawable.draw(canvas);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(0xFF0000FF);
//        RectF inset = new RectF(getLeft(), getTop(), getWidth(), getRight());
//        canvas.draw
    }
}
