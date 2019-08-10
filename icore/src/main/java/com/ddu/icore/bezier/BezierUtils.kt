package com.ddu.icore.bezier

import android.graphics.PointF

/**
 * Created by yzbzz on 2019-08-05.
 */
object BezierUtils {

    /**
     * B(t) = (1 - t) * P0 + t * p1, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 终止点
     * @return t对应的点
     */
    fun calculateBezierPointForOnePower(t: Float, p0: PointF, p1: PointF): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x = temp * p0.x + t * p1.x
        point.y = temp * p0.y + t * p1.y
        return point
    }

    /**
     * B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 终止点
     * @return t对应的点
     */
    fun calculateBezierPointForQuadratic(t: Float, p0: PointF, p1: PointF, p2: PointF): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x = temp * temp * p0.x + 2f * t * temp * p1.x + t * t * p2.x
        point.y = temp * temp * p0.y + 2f * t * temp * p1.y + t * t * p2.y
        return point
    }

    /**
     * B(t) = P0 * (1 - t)^3 + 3 * P1 * t * (1 - t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3, t ∈ [0,1]
     *
     * @param t  曲线长度比例
     * @param p0 起始点
     * @param p1 控制点
     * @param p2 控制点
     * @param p3 终止点
     * @return t对应的点
     */
    fun calculateBezierPointForThreePower(t: Float, p0: PointF, p1: PointF, p2: PointF, p3: PointF): PointF {
        val point = PointF()
        val temp = 1 - t
        point.x = p0.x * temp * temp * temp + 3f * p1.x * t * temp * temp + 3f * p2.x * t * t * temp + p3.x * t * t * t
        point.y = p0.y * temp * temp * temp + 3f * p1.y * t * temp * temp + 3f * p2.y * t * t * temp + p3.y * t * t * t
        return point
    }
}
