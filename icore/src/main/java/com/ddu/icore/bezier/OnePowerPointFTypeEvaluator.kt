package com.ddu.icore.bezier

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * Created by yzbzz on 2019-08-05.
 */
class OnePowerPointFTypeEvaluator : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        return BezierUtils.calculateBezierPointForOnePower(fraction, startValue, endValue)
    }
}
