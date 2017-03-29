package com.ddu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.ddu.R;

/**
 * Created by liuhongzhe on 16/6/6.
 */
public class CustomTextView extends AppCompatTextView {

    private int color;
    private int width;

    private Paint paint;

    public CustomTextView(@NonNull Context context) {
        this(context, null);
    }

    public CustomTextView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.icore_strike_thru);
        color = a.getColor(R.styleable.icore_strike_thru_strike_thru_color, Color.BLACK);
        width = a.getDimensionPixelSize(R.styleable.icore_strike_thru_strike_thru_width, 1);
        a.recycle();

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(width);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (null == paint) {
            initPaint();
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        int lineCount = getLineCount();
        float y;
        String text = getText().toString().trim();
        String result = "";
        for (int i = 0; i < lineCount; i++) {
            Rect rect = new Rect();
            getLineBounds(i, rect);

            int start = getLayout().getLineStart(i);
            int end = getLayout().getLineEnd(i);
            result = text.substring(start, end);

            y = rect.top + (rect.bottom - rect.top) / 2 + width / 2;

            Rect bounds = new Rect();
            Paint textPaint = getPaint();
            textPaint.getTextBounds(result, 0, result.length(), bounds);
            canvas.drawLine(bounds.left, y, bounds.right, y, paint);
            Log.v("lhz", "bounds: " + bounds.toShortString());
            Log.v("lhz", "offset: " + getLayout().getOffsetForHorizontal(i, getScaleX()));
        }

    }

}
