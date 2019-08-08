package com.ddu.icore.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointFTypeEvaluator implements TypeEvaluator<PointF> {
    /**
     * 每个估值器对应一个属性动画，每个属性动画对应唯一一个控制点
     */
    PointF control;
    /**
     * 估值器返回值
     */
    PointF mPointF = new PointF();

    public PointFTypeEvaluator(PointF control) {
        this.control = control;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return getBezierPoint(startValue,endValue,control,fraction);
    }

    private PointF getBezierPoint(PointF start, PointF end, PointF control,float t){
        mPointF.x = (1 - t) * (1 - t) * start.x + 2 * t * (1 - t) * control.x + t * t * end.x;
        mPointF.y = (1 - t) * (1 - t) * start.y + 2 * t * (1 - t) * control.y + t * t * end.y;
        return mPointF;
    }
}
