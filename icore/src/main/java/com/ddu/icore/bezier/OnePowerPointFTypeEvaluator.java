package com.ddu.icore.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by yzbzz on 2019-08-05.
 */
public class OnePowerPointFTypeEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtils.CalculateBezierPointForOnePower(fraction, startValue, endValue);
    }
}
