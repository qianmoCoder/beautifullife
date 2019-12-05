package com.ddu.ui.fragment.study.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.common.ext.dp2px
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_spannable_string_builder.*


/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("UI")
class SpannableStringBuilderFragment : DefaultFragment() {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_spannable_string_builder
    }

    override fun initView() {
        setDefaultTitle("SpannableStringBuilderFragment")
        val txtStr = resources.getString(R.string.text_img_span_content)
        val txtTail = resources.getString(R.string.text_img_span_tail)
        val content = txtStr + txtTail
        val ssb = SpannableStringBuilder(content)
        ssb.setSpan(getImageSpan(txtTail), txtStr.length, content.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        tv_txt_img_span.text = ssb
    }

    private fun getImageSpan(tag: String): ImageSpan {
        val layout = LinearLayout(ctx)
        val textView = TextView(ctx)
        textView.setBackgroundResource(R.drawable.shape_view_blue_hollow)
        textView.text = tag
        textView.textSize = 16f
        textView.setTextColor(Color.parseColor("#FDA413"))
        textView.includeFontPadding = false
        textView.setPadding(6.dp2px(ctx).toInt(), 0, 6.dp2px(ctx).toInt(), 0)
        textView.height = 17.dp2px(ctx).toInt()
        textView.gravity = Gravity.CENTER_VERTICAL

        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        // 设置左间距
        // 设置左间距
        layoutParams.leftMargin = 6.dp2px(ctx).toInt()
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        layoutParams.bottomMargin = 3.dp2px(ctx).toInt()
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
        layout.layout(0, 0, textView.measuredWidth + 9.dp2px(ctx).toInt(), textView.measuredHeight)
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
        return ImageSpan(ctx, bitmap)
    }

    companion object {

        fun newInstance(taskId: String): SpannableStringBuilderFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = SpannableStringBuilderFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
