package com.ddu.icore.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by yzbzz on 2016/10/27.
 */

public class AnimatorUtils {

    static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    static final Interpolator FAST_OUT_LINEAR_IN_INTERPOLATOR = new FastOutLinearInInterpolator();
    static final Interpolator LINEAR_OUT_SLOW_IN_INTERPOLATOR = new LinearOutSlowInInterpolator();
    static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    /**
     * Linear interpolation between {@code startValue} and {@code endValue} by {@code fraction}.
     */
    static float lerp(float startValue, float endValue, float fraction) {
        return startValue + (fraction * (endValue - startValue));
    }

    static int lerp(int startValue, int endValue, float fraction) {
        return startValue + Math.round(fraction * (endValue - startValue));
    }

    public static ObjectAnimator rotationX(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ROTATION_X, values);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

    public static ObjectAnimator rotationY(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ROTATION, values);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

    public static ObjectAnimator translation(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.TRANSLATION_X, values);
        objectAnimator.setDuration(duration);
//        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
//        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        return objectAnimator;
    }

    public static ObjectAnimator translationY(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, values);
        objectAnimator.setDuration(duration);
//        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
//        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        return objectAnimator;
    }

    public static ObjectAnimator alpha(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ALPHA, values);
        objectAnimator.setDuration(duration);
//        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
//        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        return objectAnimator;
    }

    public static ObjectAnimator scaleX(View target, long duration, boolean isLooper, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.SCALE_X, values);
        objectAnimator.setDuration(duration);
        if (isLooper) {
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        return objectAnimator;
    }

    public static ObjectAnimator scaleY(View target, long duration, boolean isLooper, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.SCALE_Y, values);
        objectAnimator.setDuration(duration);
        if (isLooper) {
            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        return objectAnimator;
    }

    public static AnimatorSet scale(View target, long duration, float... values) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(scaleX(target, duration, false, values), scaleY(target, duration, false, values));
        return animatorSet;
    }

    public static ObjectAnimator scaleByObjectAnimator(View target, long duration, float... values) {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, scaleX, scaleY);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }

    public static AnimatorSet composeIn(View first, View second, View three) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(translationY(first, 300, -first.getHeight(), 0), alpha(second, 300, 0, 1), rotationY(three, 300, 0f, 45f));
        return animatorSet;
    }

    public static AnimatorSet composeOut(View first, View second, View three) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(translationY(first, 300, 0, -first.getHeight()), alpha(second, 300, 1, 0), rotationY(three, 300, 45f, 0));
        return animatorSet;
    }

    public static ValueAnimator composeIn(View target) {
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -100, 0);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1);
        return ObjectAnimator.ofPropertyValuesHolder(target, pvh1, pvh2).setDuration(300);
    }

    public static ValueAnimator composeOut(View target) {
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0, -100);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat(View.ALPHA, 1, 0);
        return ObjectAnimator.ofPropertyValuesHolder(target, pvh1, pvh2).setDuration(300);
    }
}
