package com.ddu.ui.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import com.ddu.ui.view.BaseFloatingPathAnimator;
import com.ddu.ui.view.FloatingTextView;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
/**
 * Author UFreedom
 * 
 */
public class CurvePathFloatingAnimator extends BaseFloatingPathAnimator {

    
    @Override
    public void applyFloatingPathAnimation(@NonNull final FloatingTextView view, float start, float end) {
        ValueAnimator translateAnimator;
        ValueAnimator alphaAnimator;
        
        Spring scaleAnim = createSpringByBouncinessAndSpeed(11, 15)
                .addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(@NonNull Spring spring) {
                        float transition = transition((float) spring.getCurrentValue(), 0.0f, 1.0f);
                        view.setScaleX(transition);
                        view.setScaleY(transition);
                    }
                });


        translateAnimator = ObjectAnimator.ofFloat(start, end);
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                float pos[] = getFloatingPosition(value);
                float x = pos[0];
                float y = pos[1];
                view.setTranslationX(x);
                view.setTranslationY(y);

            }
        });
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationX(0);
                view.setTranslationY(0);
                view.setAlpha(0f);
            }
        });


        alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0f);
        alphaAnimator.setDuration(3000);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });


        scaleAnim.setEndValue(1f);
        translateAnimator.setDuration(3000);
        translateAnimator.setStartDelay(50);
        translateAnimator.start();
        alphaAnimator.start();

    }
}
