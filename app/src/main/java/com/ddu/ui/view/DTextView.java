package com.ddu.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by liuhongzhe on 16/6/6.
 */
public class DTextView extends AppCompatTextView {

    public DTextView(@NonNull Context context) {
        this(context, null);
    }

    public DTextView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        Path path = new Path();
        path.moveTo(width, 0);
        path.lineTo(width - 50, height);
        path.lineTo(width, height);
        path.close();
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#99ffffff"));
        canvas.drawPath(path, paint);
    }

}
