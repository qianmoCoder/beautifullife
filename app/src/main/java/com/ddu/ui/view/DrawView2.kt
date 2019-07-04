package com.ddu.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by yzbzz on 2018/4/8.
 */
class DrawView2 : View {

    val paint = Paint()
    var mWidth = 0f
    var mHeight = 0f

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLUE
    }

    val rect = RectF()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawView(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
    }

    private fun drawView(canvas: Canvas) {

        val left = (mWidth - 100) / 2f

        rect.set(left, 0f, left + 100, 100f)

        canvas.drawArc(rect, 0f, -180f, false, paint)

        rect.set(left, 50f, left + 100, mHeight)
        canvas.drawRect(rect, paint)


        // 用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
//        canvas.save()
//        paint.color = Color.BLUE
//        paint.style = Paint.Style.FILL
//        canvas.clipRect(rect.left, rect.top, rect.right, rect.bottom - 45f, Region.Op.INTERSECT)
//        canvas.drawRoundRect(rect, 45f, 45f, paint)
//
//        // 用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
//        // save和restore要配对使用(restore可以比save少，但不能多)，如果restore调用次数比save多，会引发Error。save和restore之间，往往夹杂的是对Canvas的特殊操作
//        canvas.restore()
    }
}