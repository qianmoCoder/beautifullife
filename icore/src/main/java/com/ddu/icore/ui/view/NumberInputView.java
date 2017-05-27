package com.ddu.icore.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ddu.icore.R;
import com.ddu.icore.util.sys.DensityUtils;
import com.ddu.icore.util.sys.ViewUtils;

public class NumberInputView extends FrameLayout implements TextWatcher {

    private EditText mHintEditText;

    private int maxLength;

    private static final int DEFAULT_MAX_LENGTH = 4;
    private LinearLayout linearLayout;

    private String mCurrentText;
    private String mBeforeText;


    public NumberInputView(Context context) {
        super(context);
        init();
    }


    public NumberInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHintEditText = new HindEditText(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mHintEditText, params);
        mHintEditText.addTextChangedListener(this);
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(linearLayout, params);
        setMaxLength(DEFAULT_MAX_LENGTH);
    }

    public void clearText() {
        if (mHintEditText != null) {
            mHintEditText.setText("");
        }
    }

    private void initChildView() {
        linearLayout.removeAllViews();
        for (int i = 0; i < maxLength; i++) {
            ItemView textView = new ItemView(getContext());
            textView.setIncludeFontPadding(false);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, 0, 0, 0);
            textView.setTextSize(36);
            textView.setTextColor(Color.parseColor("#666666"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            linearLayout.addView(textView, layoutParams);
        }
    }

    public void setMaxLength(int maxLength) {
        if (maxLength < 1) {
            return;
        }
        this.maxLength = maxLength;
        mHintEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        initChildView();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mBeforeText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCurrentText = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        int cLength = mCurrentText.length();
        int bLength = mBeforeText.length();
        if (cLength > bLength) {
            if (cLength > maxLength) {
                return;
            }
            for (int i = bLength; i < cLength; i++) {
                ItemView childAt = (ItemView) linearLayout.getChildAt(i);
                String substring = mCurrentText.substring(i, i + 1);
                childAt.setWord(substring);
            }
        } else if (cLength < bLength) {
            if (bLength > maxLength) {
                return;
            }
            for (int i = cLength; i < bLength; i++) {
                ItemView childAt = (ItemView) linearLayout.getChildAt(i);
                childAt.clear();
            }
        }
        if (cLength > maxLength - 1) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (onInputCallback != null) {
                        onInputCallback.onInputComplete(mCurrentText);
                    }
                }
            }, 300);
        }
    }


    public static class HindEditText extends AppCompatEditText {

        public HindEditText(Context context) {
            super(context);
            init();
        }

        public HindEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public HindEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @Override
        protected void onDraw(Canvas canvas) {
        }

        private void init() {
            setBackgroundDrawable(null);
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setCursorVisible(false);
        }
    }

    public static class ItemView extends ImageTextView {

        private boolean stopRunnable = false;

        public ItemView(Context context) {
            super(context);
            init();
        }

        public ItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();

        }

        private void init() {

        }

        public void setWord(String word) {
            if (TextUtils.isEmpty(word)) {
                clear();
                return;
            }
            isSelected(true);
            setText(word);
            stopRunnable = false;
            postDelayed(runnable, 200);
        }

        public void clear() {
            stopRunnable = true;
            isSelected(false);
            setText("");
        }

        public Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (!stopRunnable) {
                    isSelected(true);
                }
            }
        };

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            stopRunnable = true;
        }
    }

    public static class ImageTextView extends AppCompatTextView {

        private int borderWidth;
        private float boxRadius;

        private Paint mBorderPaint; //边框画笔
        @ColorInt
        private int borderNotFocusedColor; //边框未选中时的颜色
        @ColorInt
        private int borderFocusedColor; //边框选中时的颜色
        private int mWidth;
        private int mHeight;

        private int offset = 2;

        public ImageTextView(Context context) {
            super(context);
            init(context);
        }

        public ImageTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(Context context) {
            borderNotFocusedColor = ViewUtils.getColor(getContext(), R.color.c_cccccc);
            borderFocusedColor = ViewUtils.getColor(getContext(), R.color.c_397ede);
            borderWidth = DensityUtils.dip2px(context, 1);
            boxRadius = DensityUtils.dip2px(context, 3);
            mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBorderPaint.setStrokeWidth(borderWidth);
            mBorderPaint.setColor(borderNotFocusedColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            drawBackground(canvas);
            super.onDraw(canvas);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mWidth = w;
            mHeight = h;
            isSelected(false);
        }


        public void isSelected(boolean isSelect) {
            mBorderPaint.setColor(isSelect ? borderFocusedColor : borderNotFocusedColor);
        }

        private void drawBackground(Canvas canvas) {
            int minLength = Math.min(mWidth, mHeight);
            int point = minLength;

            int left = Math.abs(mWidth - point) / 2;
            int top = Math.abs(mHeight - point) / 2;
            RectF rect = new RectF(left + offset, top + offset, left + point - offset, top + point - offset);
            canvas.drawRoundRect(rect, boxRadius, boxRadius, mBorderPaint);
        }
    }

    public interface OnInputCallback {

        void onInputComplete(String inputText);
    }

    private OnInputCallback onInputCallback;

    public void setOnInputCallback(OnInputCallback onInputCallback) {
        this.onInputCallback = onInputCallback;
    }
}
