package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui_alipay.*
import kotlinx.android.synthetic.main.include_open.*
import kotlinx.android.synthetic.main.include_toolbar_close.*
import kotlinx.android.synthetic.main.include_toolbar_open.*

/**
 * Created by lhz on 16/5/6.
 */
@Element("UI")
class AliPayFragment : DefaultFragment(), AppBarLayout.OnOffsetChangedListener {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_alipay
    }

    override fun initView() {
        app_bar.addOnOffsetChangedListener(this)
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        fun newInstance(taskId: String): AliPayFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = AliPayFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        app_bar.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        //垂直方向偏移量
        val offset = Math.abs(verticalOffset)
        //最大偏移距离
        val scrollRange = appBarLayout.totalScrollRange
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            include_toolbar_open.setVisibility(View.VISIBLE)
            include_toolbar_close.setVisibility(View.GONE)
            //根据偏移百分比 计算透明值
            val scale2 = offset.toFloat() / (scrollRange / 2)
            val alpha2 = (255 * scale2).toInt()
            bg_toolbar_open.setBackgroundColor(Color.argb(alpha2, 25, 131, 209))
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            include_toolbar_close.setVisibility(View.VISIBLE)
            include_toolbar_open.setVisibility(View.GONE)
            val scale3 = (scrollRange - offset).toFloat() / (scrollRange / 2)
            val alpha3 = (255 * scale3).toInt()
            bg_toolbar_close.setBackgroundColor(Color.argb(alpha3, 25, 131, 209))
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        val scale = offset.toFloat() / scrollRange
        val alpha = (255 * scale).toInt()
        bg_content.setBackgroundColor(Color.argb(alpha, 25, 131, 209))
    }
}
