package com.ddu.icore.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by yzbzz on 2016/10/27.
 */

public class AnimatorUtils {

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
