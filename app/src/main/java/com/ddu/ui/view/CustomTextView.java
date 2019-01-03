package com.ddu.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ddu.R;

/**
 * Created by liuhongzhe on 16/6/6.
 */
public class CustomTextView extends AppCompatTextView {

    private int color;
    private int width;
    private int offset;

    private Paint paint;
    private Rect bounds;

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
        offset = a.getDimensionPixelOffset(R.styleable.icore_strike_thru_strike_thru_offset, 0);
        a.recycle();

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(width);

        bounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (null == paint) {
            init();
        }
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrikeThruText(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        String text = getText().toString();

        int width = getWidth();
        int height = getHeight();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int lineY = height / 2;

        Paint textPaint = getPaint();

        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int lineWidth = (width - bounds.width()) / 2 - offset;

        int rightLineX = width - lineWidth;

        canvas.drawLine(paddingLeft, lineY, lineWidth, lineY, paint);
        canvas.drawLine(rightLineX, lineY, width - paddingRight, lineY, paint);


//        int lineCount = getLineCount();
//        float y;
//
//        String result = "";
//        for (int i = 0; i < lineCount; i++) {
//            Rect rect = new Rect();
//            getLineBounds(i, rect);
//
//            int start = getLayout().getLineStart(i);
//            int end = getLayout().getLineEnd(i);
//            result = text.substring(start, end);
//
//            y = rect.top + (rect.bottom - rect.top) / 2 + width / 2;
//
//            Rect bounds = new Rect();
//            Paint textPaint = getPaint();
//            textPaint.getTextBounds(result, 0, result.length(), bounds);
//            int widht = getWidth() - bounds.right;
//            canvas.drawLine(bounds.left + getPaddingLeft(), y, widht / 2, y, paint);
//            canvas.drawLine(bounds.left + getPaddingLeft() + widht / 2 + bounds.right, y, widht / 2, y, paint);
//        }

    }

}
