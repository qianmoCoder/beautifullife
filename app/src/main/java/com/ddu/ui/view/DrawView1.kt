package com.ddu.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by yzbzz on 2018/4/8.
 */
class DrawView1 : View {

    val paint = Paint()
    val path = Path()

    init {
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(RectF(100f, 100f, 300f, 300f), -225f, 225f)
        path.arcTo(RectF(200f, 100f, 500f, 300f), -180f, 225f, false)
        path.lineTo(400f, 542f)
    }


    var y1 = 0f

    val rect = RectF()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.isAntiAlias = true
        drawPath(canvas)
        drawView(canvas)
    }

    private fun drawPath(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    private fun drawView(canvas: Canvas) {
        y1 += 100

        canvas.drawCircle(100f, y1, 90f, paint)
        canvas.drawColor(Color.parseColor("#88880000"))

        canvas.drawRect(300f, y1, 500f, 190f, paint)


        // point
        paint.strokeWidth = 20f
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(600f,  y1 , paint)

        paint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPoint(700f,  y1 , paint)

        paint.strokeCap = Paint.Cap.BUTT
        canvas.drawPoint(800f, y1, paint)

        y1 += 100f
        // oval
        canvas.drawPoint(5f, y1 - 5, paint)
        rect.set(10f, y1, 500f, 400f)
        canvas.drawOval(rect, paint)
        canvas.drawPoint(500f, y1, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        rect.set(550f, y1, 1040f, 400f)
        canvas.drawOval(rect, paint)

        y1 += 200
        // roundRect
        paint.style = Paint.Style.FILL
        rect.set(10f, y1, 500f, 600f)
        canvas.drawRoundRect(rect, 50f, 50f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        rect.set(550f, y1, 1040f, 600f)
        canvas.drawRoundRect(rect, 50f, 50f, paint)


        // drawArc
        y1 += 200
        paint.style = Paint.Style.FILL

        canvas.drawPoint(5f, y1 - 5, paint)
        canvas.drawPoint(615f, y1 + 405, paint)

        rect.set(10f, y1, 610f, y1 + 400)
        canvas.drawArc(rect, -110f, 100f, true, paint) //绘制扇形

        canvas.drawArc(rect, 20f, 140f, false, paint) // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(rect, 180f, 60f, false, paint) // 绘制不封口的弧形

    }
}