package com.ddu.icore.bezier

import android.animation.TypeEvaluator
import android.graphics.PointF

/**
 * Created by yzbzz on 2019-08-05.
 */
class ThreePowerPointFTypeEvaluator(private val mControl1: PointF, private val mControl2: PointF) :
    TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        return BezierUtils.calculateBezierPointForThreePower(fraction, startValue, mControl1, mControl2, endValue)
    }
}
