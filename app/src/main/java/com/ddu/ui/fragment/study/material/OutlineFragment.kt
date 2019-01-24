package com.ddu.ui.fragment.study.material

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_outline.*

/**
 * Created by yzbzz on 2019/1/24.
 */
@IElement("MD")
class OutlineFragment : DefaultFragment() {

    override fun getLayoutId() = R.layout.fragment_ui_outline

    override fun initView() {
        tab1_spread_w.outlineProvider = object : ViewOutlineProvider() {

            override fun getOutline(view: View, outline: Outline?) {
                outline?.setRoundRect(0, 0, view.width, view.height, 25.0f)
            }
        }
        tab1_spread_w.clipToOutline = true

        tab2_spread_w.outlineProvider = object : ViewOutlineProvider() {

            override fun getOutline(view: View, outline: Outline?) {
                val rect = Rect()
                rect.left = 0
                rect.top = 0
                rect.right = view.width
                rect.bottom = view.height
                outline?.setOval(rect)
            }
        }
        tab2_spread_w.clipToOutline = true
    }

}