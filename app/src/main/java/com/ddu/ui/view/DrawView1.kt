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
class DrawView1 : View {

    val paint = Paint()

    var y1 = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.isAntiAlias = true
        canvas.drawCircle(100f, 100f, 90f, paint)
        canvas.drawColor(Color.parseColor("#88880000"))

        canvas.drawRect(300f, 10f, 500f, 190f, paint)

        // point
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(600f, 100f, paint)

        paint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPoint(700f, 100f, paint)

        paint.strokeCap = Paint.Cap.BUTT
        canvas.drawPoint(800f, 100f, paint)


        y1 = 300f
        // oval
        canvas.drawOval(RectF(10f, y1, 500f, 400f), paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        canvas.drawOval(RectF(550f, y1, 1040f, 400f), paint)

        y1 += 200
        // roundRect
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(RectF(10f, y1, 500f, 600f), 50f, 50f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        canvas.drawRoundRect(RectF(550f, y1, 1040f, 600f), 50f, 50f, paint)

        y1 += 200
        paint.style = Paint.Style.FILL
        // 绘制扇形
        canvas.drawArc(RectF(10f, y1, 500f, y1 + 200), -110f, 100f, true, paint)
        // 绘制弧形
        canvas.drawArc(RectF(10f, y1, 800f, y1 + 200), 20f, 140f, false, paint)
        paint.style = Paint.Style.STROKE // 画线模式
        // 绘制不封口的弧形
        canvas.drawArc(RectF(10f, y1, 800f, y1 + 200), 180f, 60f, false, paint)

    }
}