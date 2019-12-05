package com.ddu.ui.activity.phrase

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ddu.R
import com.ddu.icore.common.ext.dp2px
import com.ddu.icore.util.StylePhrase
import kotlinx.android.synthetic.main.activity_custom_separator.*


class AdvancedSeparatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced_separator)

        val customSeparatorString = getString(R.string.text_advanced)

        tv_description.text = "高级用法"
        tv_original.text = customSeparatorString

        tv_separator.text = "{}"

        // 设置字体和颜色
        val stylePhrase = StylePhrase(customSeparatorString)
        stylePhrase.firstBuilder.addParcelableSpan(getImageSpan("abc"))
        tv_content.text = stylePhrase.format()
    }

    private fun getImageSpan(tag: String): ImageSpan {
        val layout = LinearLayout(this)
        val textView = TextView(this)
        textView.text = tag
        textView.textSize = 16f
        textView.setBackgroundResource(R.drawable.shape_view_green_hollow)
        textView.setTextColor(Color.parseColor("#FDA413"))
        textView.includeFontPadding = false
        textView.setPadding(6.dp2px(this).toInt(), 0, 6.dp2px(this).toInt(), 0)
        textView.height = 17.dp2px(this).toInt()
        textView.gravity = Gravity.CENTER_VERTICAL

        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        // 设置左间距
        // 设置左间距
        layoutParams.leftMargin = 6.dp2px(this).toInt()
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        layoutParams.bottomMargin = 3.dp2px(this).toInt()
        layout.addView(textView, layoutParams)

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.isDrawingCacheEnabled = true
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        // 给上方设置的margin留出空间
        // 给上方设置的margin留出空间
        layout.layout(0, 0, textView.measuredWidth + 9.dp2px(this).toInt(), textView.measuredHeight)
        // 获取bitmap对象
        // 获取bitmap对象
        val bitmap: Bitmap = Bitmap.createBitmap(layout.drawingCache)
        //千万别忘最后一步
        //千万别忘最后一步
        layout.destroyDrawingCache()

        /**
         * 第三步，通过bitmap生成我们需要的ImageSpan对象
         */
        /**
         * 第三步，通过bitmap生成我们需要的ImageSpan对象
         */
        return ImageSpan(this, bitmap)
    }
}
