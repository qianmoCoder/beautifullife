package com.ddu.icore.util;

import android.animation.ObjectAnimator;
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
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ROTATION_X, values);
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

    public static ObjectAnimator alpha(View target, long duration, float... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, View.ALPHA, values);
        objectAnimator.setDuration(duration);
//        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
//        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        return objectAnimator;
    }
}
