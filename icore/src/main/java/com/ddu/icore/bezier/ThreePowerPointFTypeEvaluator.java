package com.ddu.icore.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by yzbzz on 2019-08-05.
 */
public class ThreePowerPointFTypeEvaluator implements TypeEvaluator<PointF> {

    private PointF mControl1;
    private PointF mControl2;

    public ThreePowerPointFTypeEvaluator(PointF control1, PointF control2) {
        this.mControl1 = control1;
        this.mControl2 = control2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtils.CalculateBezierPointForThreePower(fraction, startValue, mControl1, mControl2, endValue);
    }
}
