package com.ddu.ui.view;

import android.graphics.PathMeasure;
import android.support.annotation.NonNull;

import com.ddu.ui.effect.ReboundFloatingAnimator;


/**
 * Author UFreedom
 * 
 */
public abstract class BaseFloatingPathAnimator extends ReboundFloatingAnimator implements FloatingPathAnimator {
    
    private PathMeasure pathMeasure;
    private float pos[];

    public float[] getFloatingPosition(float progress) {
        pathMeasure.getPosTan(progress, pos, null);
        return pos;
    }
 
    
    @Override
    public void applyFloatingAnimation(@NonNull FloatingTextView view) {
        pathMeasure = view.getPathMeasure();
        if (pathMeasure == null) {
            return;
        }
        pos = new float[2];
        applyFloatingPathAnimation(view,0,pathMeasure.getLength());
    }
    
}
