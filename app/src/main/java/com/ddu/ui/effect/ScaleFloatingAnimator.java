package com.ddu.ui.effect;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;

import com.ddu.ui.view.FloatingTextView;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;

/**
 * Author UFreedom
 * 
 */
public class ScaleFloatingAnimator extends ReboundFloatingAnimator {

    private long duration;

    public ScaleFloatingAnimator() {
        duration = 1000;
    }

    public ScaleFloatingAnimator(long duration) {
        this.duration = duration;
    }


    @Override
    public void applyFloatingAnimation(@NonNull final FloatingTextView view) {

        Spring scaleAnim = createSpringByBouncinessAndSpeed(10, 15)
                .addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(@NonNull Spring spring) {
                        float transition = transition((float) spring.getCurrentValue(), 0.0f, 1.0f);
                        view.setScaleX(transition);
                        view.setScaleY(transition);
                    }
                });
        
        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0.0f);
        alphaAnimator.setDuration(duration);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleAnim.setEndValue(1f);
        alphaAnimator.start();
    }


}
