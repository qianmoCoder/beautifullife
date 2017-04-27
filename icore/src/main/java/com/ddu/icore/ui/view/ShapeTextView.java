package com.ddu.icore.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ddu.icore.R;

public class ShapeTextView extends AppCompatTextView {

    private int mNormalTextColor = 0;
    private int mPressedTextColor = 0;
    private int mDisableTextColor = 0;

    private Drawable mBackground;
    private ColorDrawable mColorDrawable;

    private ColorStateList mColorStateList;
    private GradientDrawable mNormalBackground;
    private GradientDrawable mPressedBackground;
    private GradientDrawable mDisableBackground;

    //animation duration
    private int mDuration = 0;

    //radius
    private float mRadius = 0;
    private boolean mRound;

    private boolean mSegmented;

    //stroke
    private float mStrokeDashWidth = 0;
    private float mStrokeDashGap = 0;

    private int mNormalStrokeWidth = 0;
    private int mPressedStrokeWidth = 0;
    private int mDisableStrokeWidth = 0;

    private int mNormalStrokeColor = 0;
    private int mPressedStrokeColor = 0;
    private int mDisableStrokeColor = 0;

    //background color
    private int mNormalBackgroundColor = 0;
    private int mPressedBackgroundColor = 0;
    private int mDisableBackgroundColor = 0;


    private int[][] states;

    StateListDrawable mStateBackground;

    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {

        mBackground = getBackground();

        mNormalBackground = new GradientDrawable();
        mPressedBackground = new GradientDrawable();
        mDisableBackground = new GradientDrawable();

        if (null != mBackground) {
            setBackground(mBackground);
        } else {
            states = new int[4][];

            mStateBackground = new StateListDrawable();

            //pressed, focused, normal, Disable
            states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
            states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
            states[3] = new int[]{-android.R.attr.state_enabled};
            states[2] = new int[]{android.R.attr.state_enabled};

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeView);

            //get original text color as default
            //set text color
            mColorStateList = getTextColors();
            int mDefaultNormalTextColor = mColorStateList.getColorForState(states[2], getCurrentTextColor());
            int mDefaultPressedTextColor = mColorStateList.getColorForState(states[0], getCurrentTextColor());
            int mDefaultDisableTextColor = mColorStateList.getColorForState(states[3], getCurrentTextColor());

            mNormalTextColor = a.getColor(R.styleable.ShapeView_normalTextColor, mDefaultNormalTextColor);
            mPressedTextColor = a.getColor(R.styleable.ShapeView_pressedTextColor, mDefaultPressedTextColor);
            mDisableTextColor = a.getColor(R.styleable.ShapeView_disableTextColor, mDefaultDisableTextColor);
            setTextColor();

            //set animation duration
            mDuration = a.getInteger(R.styleable.ShapeView_animationDuration, mDuration);
            mStateBackground.setEnterFadeDuration(mDuration);
            mStateBackground.setExitFadeDuration(mDuration);

            //set background color
            if (mBackground instanceof ColorDrawable) {
                mColorDrawable = (ColorDrawable) mBackground;
            }
            int defValue = 0;
            if (null != mColorDrawable) {
                defValue = mColorDrawable.getColor();
            }
            mNormalBackgroundColor = a.getColor(R.styleable.ShapeView_normalBackgroundColor, defValue);
            mPressedBackgroundColor = a.getColor(R.styleable.ShapeView_pressedBackgroundColor, 0);
            mDisableBackgroundColor = a.getColor(R.styleable.ShapeView_disableBackgroundColor, 0);

            mNormalBackground.setColor(mNormalBackgroundColor);
            mPressedBackground.setColor(mPressedBackgroundColor);
            mDisableBackground.setColor(mDisableBackgroundColor);

            //set radius
            mRadius = a.getDimensionPixelSize(R.styleable.ShapeView_radius, 0);
            mNormalBackground.setCornerRadius(mRadius);
            mPressedBackground.setCornerRadius(mRadius);
            mDisableBackground.setCornerRadius(mRadius);

            mRound = a.getBoolean(R.styleable.ShapeView_round, false);
            mSegmented = a.getBoolean(R.styleable.ShapeView_segmented, false);

            //set stroke
            mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.ShapeView_strokeDashWidth, 0);
            mStrokeDashGap = a.getDimensionPixelSize(R.styleable.ShapeView_strokeDashWidth, 0);
            mNormalStrokeWidth = a.getDimensionPixelSize(R.styleable.ShapeView_normalStrokeWidth, 0);
            mPressedStrokeWidth = a.getDimensionPixelSize(R.styleable.ShapeView_pressedStrokeWidth, 0);
            mDisableStrokeWidth = a.getDimensionPixelSize(R.styleable.ShapeView_disableStrokeWidth, 0);
            mNormalStrokeColor = a.getColor(R.styleable.ShapeView_normalStrokeColor, 0);
            mPressedStrokeColor = a.getColor(R.styleable.ShapeView_pressedStrokeColor, 0);
            mDisableStrokeColor = a.getColor(R.styleable.ShapeView_disableStrokeColor, 0);
            setStroke();

            //set background
            mStateBackground.addState(states[0], mPressedBackground);
            mStateBackground.addState(states[1], mPressedBackground);
            mStateBackground.addState(states[3], mDisableBackground);
            mStateBackground.addState(states[2], mNormalBackground);

            setBackgroundDrawable(mStateBackground);
            a.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRound) {
            setRound(mRound);
        } else if (mSegmented) {
            setSegmented(mSegmented);
        }
    }

    public void setNormalStrokeColor(@ColorInt int normalStrokeColor) {
        this.mNormalStrokeColor = normalStrokeColor;
        setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth);
    }

    public void setPressedStrokeColor(@ColorInt int pressedStrokeColor) {
        this.mPressedStrokeColor = pressedStrokeColor;
        setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth);
    }

    public void setDisableStrokeColor(@ColorInt int DisableStrokeColor) {
        this.mDisableStrokeColor = DisableStrokeColor;
        setStroke(mDisableBackground, mDisableStrokeColor, mDisableStrokeWidth);
    }

    public void setStateStrokeColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int Disable) {
        mNormalStrokeColor = normal;
        mPressedStrokeColor = pressed;
        mDisableStrokeColor = Disable;
        setStroke();
    }

    public void setNormalStrokeWidth(int normalStrokeWidth) {
        this.mNormalStrokeWidth = normalStrokeWidth;
        setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth);
    }

    public void setPressedStrokeWidth(int pressedStrokeWidth) {
        this.mPressedStrokeWidth = pressedStrokeWidth;
        setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth);
    }

    public void setDisableStrokeWidth(int DisableStrokeWidth) {
        this.mDisableStrokeWidth = DisableStrokeWidth;
        setStroke(mDisableBackground, mDisableStrokeColor, mDisableStrokeWidth);
    }

    public void setStateStrokeWidth(int normal, int pressed, int Disable) {
        mNormalStrokeWidth = normal;
        mPressedStrokeWidth = pressed;
        mDisableStrokeWidth = Disable;
        setStroke();
    }

    public void setStrokeDash(float strokeDashWidth, float strokeDashGap) {
        this.mStrokeDashWidth = strokeDashWidth;
        this.mStrokeDashGap = strokeDashWidth;
        setStroke();
    }

    private void setStroke() {
        setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth);
        setStroke(mPressedBackground, mPressedStrokeColor, mPressedStrokeWidth);
        setStroke(mDisableBackground, mDisableStrokeColor, mDisableStrokeWidth);
    }

    private void setStroke(GradientDrawable mBackground, int mStrokeColor, int mStrokeWidth) {
        if (null != mBackground) {
            mBackground.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashWidth, mStrokeDashGap);
        }
    }

    public void setRadius(@FloatRange(from = 0) float radius) {
        this.mRadius = radius;
        mNormalBackground.setCornerRadius(mRadius);
        mPressedBackground.setCornerRadius(mRadius);
        mDisableBackground.setCornerRadius(mRadius);
    }

    public void setRound(boolean round) {
        this.mRound = round;
        post(new Runnable() {
            @Override
            public void run() {
                int height = getMeasuredHeight();
                if (mRound) {
                    setRadius(height / 2f);
                }
            }
        });
    }

    public void setSegmented(boolean isSegmented) {
        this.mSegmented = isSegmented;
        if (isSegmented) {
            post(new Runnable() {
                @Override
                public void run() {
                    float radius = getMeasuredHeight();
                    float[] radii = new float[]{radius, radius, 0, 0, 0, 0, radius, radius};
                    setRadius(radii);
                }
            });

        }
    }


    public void setRadius(float[] radii) {
        mNormalBackground.setCornerRadii(radii);
        mPressedBackground.setCornerRadii(radii);
        mDisableBackground.setCornerRadii(radii);
    }

    public void setStateBackgroundColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int Disable) {
        mPressedBackgroundColor = normal;
        mNormalBackgroundColor = pressed;
        mDisableBackgroundColor = Disable;
        mNormalBackground.setColor(mNormalBackgroundColor);
        mPressedBackground.setColor(mPressedBackgroundColor);
        mDisableBackground.setColor(mDisableBackgroundColor);
    }

    public void setNormalBackgroundColor(@ColorInt int normalBackgroundColor) {
        this.mNormalBackgroundColor = normalBackgroundColor;
        mNormalBackground.setColor(mNormalBackgroundColor);
    }

    public void setPressedBackgroundColor(@ColorInt int pressedBackgroundColor) {
        this.mPressedBackgroundColor = pressedBackgroundColor;
        mPressedBackground.setColor(mPressedBackgroundColor);
    }

    public void setDisableBackgroundColor(@ColorInt int DisableBackgroundColor) {
        this.mDisableBackgroundColor = DisableBackgroundColor;
        mDisableBackground.setColor(mDisableBackgroundColor);
    }

    public void setAnimationDuration(@IntRange(from = 0) int duration) {
        this.mDuration = duration;
        mStateBackground.setEnterFadeDuration(mDuration);
    }

    private void setTextColor() {
        int[] colors = new int[]{mPressedTextColor, mPressedTextColor, mNormalTextColor, mDisableTextColor};
        mColorStateList = new ColorStateList(states, colors);
        setTextColor(mColorStateList);
    }

    public void setStateTextColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int Disable) {
        this.mNormalTextColor = normal;
        this.mPressedTextColor = pressed;
        this.mDisableTextColor = Disable;
        setTextColor();
    }

    public void setNormalTextColor(@ColorInt int normalTextColor) {
        this.mNormalTextColor = normalTextColor;
        setTextColor();

    }

    public void setPressedTextColor(@ColorInt int pressedTextColor) {
        this.mPressedTextColor = pressedTextColor;
        setTextColor();
    }

    public void setDisableTextColor(@ColorInt int DisableTextColor) {
        this.mDisableTextColor = DisableTextColor;
        setTextColor();
    }
}
