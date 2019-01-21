package com.ddu.icore.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 解决TextView换行存在的排版不整齐问题
 * 缺点：对于wap_content时显示处理的不好——主要是高度wrap_content时，
 *      字体太大时引起的该行高度变化未予以考虑，望其他人解决后共享
 *      只扩展了ForegroundSpan、RelativeSizeSpan
 * e-mail:2110078288@qq.com
 * Created by lqw on 2016/1/8.
 */
public class SuperTextView extends AppCompatTextView {
    private int mWidth; //控件宽度
    private int mHeight; //控件高度
    private static String mContent = ""; //textview中的内容 如果不是static的话，xml里面text无法同步
    private int mContentWidth; // 内容宽度
    private ArrayList<FGSpan> fgSpans = new ArrayList<>(); // 存放所有ForegroundSpan
    private ArrayList<RLSpan> rlSpans = new ArrayList<>(); //存放所有的RelativeSizeSpan
    private int fgSpanStart; // 某个ForegroundSpan的开始位置
    private int rlSpanStart; // 某个RelativeSizeSpan的开始位置
    private int fgSpanEnd; // 某个ForegroundSpan的结束位置
    private int rlSpanEnd; // 某个RelativeSizeSpan的结束位置
    private Paint paint;

    public SuperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        paint = new Paint();
        /*
        设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        //父控件允许子控件的最大宽度,包括子控件设置的具体值或者fill_parent
        if(specMode == MeasureSpec.EXACTLY) { //fill_parent，也相当于设置了exactly
            mWidth = specSize;
        } else {
            //子控件需要的宽度
            int desiredSize = (int) (getPaddingLeft() + getPaddingRight() + mContent.length() * getTextSize());
            if(specMode == MeasureSpec.AT_MOST) { //wrap_content
                //两者取最小
                desiredSize = Math.min(specSize, desiredSize);
            }
            mWidth = desiredSize;
        }

//        /*
//        根据文本内容设置高度
//         */
//        Rect textBound = new Rect();
//        paint = new Paint();
//        paint.setTextSize(getTextSize());
//        paint.getTextBounds(mContent,0,mContent.length(),textBound);
//        mHeight = textBound.height() + getPaddingBottom() + getPaddingTop();
        mContentWidth = mWidth - getPaddingLeft() - getPaddingRight();
        setMeasuredDimension(mWidth, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //逐个字符画出
        int fgSpanIndex = 0; //标记fgSpans下标
        int rlSpanIndex = 0; //标记rlSpans下标
        paint.setColor(getCurrentTextColor());
        paint.setTextSize(getTextSize());
        float x = 0,y = 0;
        Paint.FontMetrics fm = paint.getFontMetrics();
        float baseLine = fm.descent - fm.ascent;
        y = baseLine;
        for(int i = 0; i < mContent.length(); i++) {
            /*
            画当前字符
             */
            //处理RelativeSizeSpan
            if(rlSpans != null && rlSpans.size() > 0) {
                if(rlSpanIndex < rlSpans.size()
                        && i >= rlSpans.get(rlSpanIndex).start
                        && i < rlSpans.get(rlSpanIndex).end) {
                    paint.setTextSize(rlSpans.get(rlSpanIndex).propertion*getTextSize());
                } else if(rlSpanIndex < rlSpans.size() && i >= rlSpans.get(rlSpanIndex).end) {
                    rlSpanIndex++;
                }
            }
            //处理ForegroundSpan
            if(fgSpans != null && fgSpans.size() > 0) {
                if(fgSpanIndex < fgSpans.size()
                        && i >= fgSpans.get(fgSpanIndex).start
                        && i < fgSpans.get(fgSpanIndex).end) {
                    paint.setColor(fgSpans.get(fgSpanIndex).fgColor);
                } else if(fgSpanIndex < fgSpans.size() && i >= fgSpans.get(fgSpanIndex).end) {
                    fgSpanIndex++;
                }
            }
            if(i +2 <=mContent.length()/*主要是防止后面越界*/
                    && x >= mContentWidth - paint.measureText(mContent.substring(i+1,i+2))) { //换行
                x = 0;
                y += baseLine + fm.leading;
            }
            canvas.drawText(mContent.substring(i,i+1),x,y,paint);
            x += paint.measureText(mContent.substring(i,i+1));
            paint.setTextSize(getTextSize());
            paint.setColor(getCurrentTextColor());
        }

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText("", type);
        if(text instanceof SpannableString) {
            SpannableString sStr = (SpannableString) text;
            //获取所有的ForegroundSpan
            ForegroundColorSpan[] spans =  sStr.getSpans(0, sStr.length(), ForegroundColorSpan.class);
            for(ForegroundColorSpan span:spans) {
                FGSpan fgSpan = new FGSpan();
                fgSpan.fgColor = span.getForegroundColor();
                fgSpan.start = sStr.getSpanStart(span);
                fgSpan.end = sStr.getSpanEnd(span);
                fgSpans.add(fgSpan);
            }
            //获取所有的RelativeSizeSpan
            RelativeSizeSpan[] spans2 = sStr.getSpans(0, sStr.length(), RelativeSizeSpan.class);
            for(RelativeSizeSpan span:spans2) {
                RLSpan rlSpan = new RLSpan();
                rlSpan.propertion = span.getSizeChange();
                rlSpan.start = sStr.getSpanStart(span);
                rlSpan.end = sStr.getSpanEnd(span);
                rlSpans.add(rlSpan);
            }
        }
        //内容赋值
        mContent = text.toString(); //如果mContent不是static的话，xml里面text无法同步
    }
}
