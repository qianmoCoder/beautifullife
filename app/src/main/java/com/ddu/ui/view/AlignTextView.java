package com.ddu.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by yzbzz on 2016/12/19.
 */

public class AlignTextView extends AppCompatTextView {

    private int mViewWidth;
    private int mLineY;

    public AlignTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlignTextView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int gravity = getGravity();
        if (gravity == Gravity.CENTER) {
            super.onDraw(canvas);
        } else {
            TextPaint paint = getPaint();
            paint.setColor(getCurrentTextColor());
            paint.drawableState = getDrawableState();
            mViewWidth = getMeasuredWidth();
            CharSequence text = getText().toString();
            mLineY = 0;
            mLineY += getTextSize();
            Layout layout = getLayout();
            for (int i = 0; i < layout.getLineCount(); i++) {
                int lineStart = layout.getLineStart(i);
                int lineEnd = layout.getLineEnd(i);
                CharSequence cline = text.subSequence(lineStart, lineEnd);
                String line = cline.toString();



                float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
                if (needScale(line)) {
                    if (i == layout.getLineCount() - 1) {
                        canvas.drawText(line, 0, mLineY, paint);
                    } else {
                        drawScaleText(canvas, lineStart, line, width);
                    }
                } else {
                    canvas.drawText(line, 0, mLineY, paint);
                }
                mLineY += getLineHeight();
            }
        }
    }

    public void drawText(Canvas canvas, SpannableString spannableString, Paint mTextPaint) {
        // draw each span one at a time
        int next;
        float xStart = 0;
        float xEnd;
        for (int i = 0; i < spannableString.length(); i = next) {

            // find the next span transition
            next = spannableString.nextSpanTransition(i, spannableString.length(), CharacterStyle.class);

            // measure the length of the span
            xEnd = xStart + mTextPaint.measureText(spannableString, i, next);


            // draw the text with an optional foreground color
            ForegroundColorSpan[] fgSpans = spannableString.getSpans(i, next, ForegroundColorSpan.class);
            if (fgSpans.length > 0) {
                int saveColor = mTextPaint.getColor();
                mTextPaint.setColor(fgSpans[0].getForegroundColor());
                canvas.drawText(spannableString, i, next, xStart, 0, mTextPaint);
                mTextPaint.setColor(saveColor);
            } else {
                mTextPaint.setColor(getCurrentTextColor());
                canvas.drawText(spannableString, i, next, xStart, 0, mTextPaint);
            }
            xStart = xEnd;
        }
    }

    private void drawScaleText(@NonNull Canvas canvas, int lineStart, @NonNull String line,
                               float lineWidth) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line)) {
            String blanks = "  ";
            canvas.drawText(blanks, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(blanks, getPaint());
            x += bw;
            line = line.substring(3);
        }

        float d = (mViewWidth - lineWidth) / (line.length() - 1);

        for (int i = 0; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, @NonNull String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }


    private boolean needScale(@NonNull String line) {
        if (line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }
}
