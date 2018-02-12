package com.ddu.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.ddu.R

@SuppressLint("NewApi")
class WaitingBar : LinearLayout {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    private fun init(attrs: AttributeSet?) {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        val bitmap = BitmapFactory.decodeResource(resources,
                R.drawable.office_waitingbar_indicator_sel)

        val a = context.obtainStyledAttributes(attrs, R.styleable.WaitingBarStyleable)
        val width = a.getDimensionPixelSize(R.styleable.WaitingBarStyleable_point_width, bitmap.width)
        val height = a.getDimensionPixelSize(R.styleable.WaitingBarStyleable_point_height, bitmap.height)
        a.recycle()

        val tLayoutParams = LinearLayout.LayoutParams(width, height)
        tLayoutParams.leftMargin = 15
        tLayoutParams.rightMargin = 15
        for (i in 0 until NUM) {
            val vDot = ImageView(context)
            vDot.layoutParams = tLayoutParams
            vDot.visibility = View.INVISIBLE
            vDot.setBackgroundResource(R.drawable.office_waitingbar_indicator_sel)
            addView(vDot)
        }
        UpdateHandler().sendEmptyMessage(0)
    }

    internal inner class UpdateHandler : Handler() {

        override fun handleMessage(msg: Message) {
            // TODO Auto-generated method stub
            super.handleMessage(msg)
            var tPosition = msg.what

            for (index in 0 until NUM) {
                val currentDot = this@WaitingBar
                        .getChildAt(index) as ImageView
                currentDot.visibility = View.VISIBLE
                if (tPosition == NUM) {
                    currentDot.visibility = View.INVISIBLE
                } else {
                    currentDot.visibility = if (index <= tPosition) View.VISIBLE else View.INVISIBLE
                }
            }

            if (tPosition == NUM) {
                tPosition = 0
            } else {
                tPosition++
            }
            sendEmptyMessageDelayed(tPosition, 300)
        }
    }

    companion object {
        private const val NUM = 3
    }
}

