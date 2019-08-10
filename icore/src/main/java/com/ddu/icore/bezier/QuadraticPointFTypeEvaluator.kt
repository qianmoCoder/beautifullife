package com.ddu.icore.bezier

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * Created by yzbzz on 2019-08-05.
 */
class QuadraticPointFTypeEvaluator(private val mControl: PointF) : TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        return BezierUtils.calculateBezierPointForQuadratic(fraction, startValue, mControl, endValue)
    }
}
