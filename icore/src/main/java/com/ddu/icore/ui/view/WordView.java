package com.ddu.icore.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by yzbzz on 2017/3/28.
 */

public class WordView extends AppCompatTextView {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public int      RoundRadius = 0;
    public int    BackColor   = 0xffe62565;
    public float    Rotation        = -45f;
    public int    OffsetX           = -35;
    public int    OffsetY           = -35;
    public int      ShrinkX         = -21;
    public int      ShrinkY         = 0;

    public WordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WordView(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = new RectF(ShrinkX, ShrinkY, getMeasuredWidth() - ShrinkX, getMeasuredHeight() - ShrinkY);
        if (OffsetX != 0 || OffsetY != 0) {
            canvas.translate(OffsetX, OffsetY);
        }

        if (Rotation != 0) {
            canvas.rotate(Rotation, rect.centerX(), rect.centerY());
        }

        if (RoundRadius > 0) {
            Path path = new Path();
            path.addRoundRect(rect, RoundRadius, RoundRadius, Path.Direction.CCW);
            canvas.clipPath(path);
        }

        paint.setColor(BackColor);
        canvas.drawRect(rect, paint);

        super.draw(canvas);
    }
}
