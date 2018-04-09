package com.ddu.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/**
 * Created by liuhongzhe on 16/6/6.
 */
class CustomView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val cx = 100
    private val cy = 100

    private val paint: Paint

    init {
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.isAntiAlias = true

        paint.color = Color.BLUE
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), 50f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas.drawCircle(cx.toFloat(), (cy * 2 + 20).toFloat(), 50f, paint)


        paint.color = Color.GREEN
        val rect = Rect(cx + 70, cy + 70, cx + 70 + 100, cy + 70 + 100)
        canvas.drawRect(rect, paint)

        paint.style = Paint.Style.FILL
        canvas.drawRect((cx + 70).toFloat(), (cx - 50).toFloat(), (cx + 70 + 100).toFloat(), (cx - 50 + 100).toFloat(), paint)

    }
}
