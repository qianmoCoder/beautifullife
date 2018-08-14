package com.ddu.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.ddu.R;

public final class RevealTextView extends AppCompatTextView implements Runnable, ValueAnimator.AnimatorUpdateListener {
    private int animationDuration = 300;
    @Nullable
    private String text;
    private int red;
    private int green;
    private int blue;
    private double[] alphas;

    public RevealTextView(Context context) {
        this(context, null);
    }

    public RevealTextView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealTextView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context.obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
    }


    protected void init(@NonNull TypedArray attrs) {
        try {
            animationDuration = attrs.getInteger(R.styleable.RevealTextView_rtv_duration, animationDuration);
            text = attrs.getString(R.styleable.RevealTextView_android_text);
        } finally {
            attrs.recycle();
        }

        setAnimatedText(text);
    }

    @Override
    public void run() {
        final int color = getCurrentTextColor();

        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 2f);
        animator.setDuration(animationDuration);
        animator.addUpdateListener(this);
        animator.start();
    }

    protected int clamp(double value) {
        return (int) (255f * Math.min(Math.max(value, 0f), 1f));
    }

    @Override
    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
        final float value = (float) valueAnimator.getAnimatedValue();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); i++) {
            builder.setSpan(new ForegroundColorSpan(Color.argb(clamp(value + alphas[i]), red, green, blue)), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(builder);
    }

    /**************
     *** Public ***
     **************/

    /**
     * Replay the animation.
     */
    public void replayAnimation() {
        if (null != text) {
            post(this);
        }
    }

    /**
     * Change the text and replay the animation.
     *
     * @param text Text to be shown.
     */
    public void setAnimatedText(@NonNull String text) {
        this.text = text;
        alphas = new double[text.length()];
        for (int i = 0; i < text.length(); i++) {
            alphas[i] = Math.random() - 1.0f;
        }

        setText(text);

        replayAnimation();
    }
}
