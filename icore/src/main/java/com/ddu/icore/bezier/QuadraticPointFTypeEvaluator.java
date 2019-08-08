package com.ddu.icore.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by yzbzz on 2019-08-05.
 */
public class QuadraticPointFTypeEvaluator implements TypeEvaluator<PointF> {

    private PointF mControl;

    public QuadraticPointFTypeEvaluator(PointF control) {
        this.mControl = control;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtils.CalculateBezierPointForQuadratic(fraction, startValue, mControl, endValue);
    }
}
