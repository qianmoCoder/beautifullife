package com.ddu.ui.fragment.study.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_study_view.*
import org.jetbrains.anko.horizontalPadding

/**
 * Created by yzbzz on 2017/9/1.
 */

class ViewFragment : DefaultFragment() {
    var show = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_study_view
    }

    override fun initView() {
        var gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = 4f
        val color = resources.getColor(R.color.c_252525)
        gradientDrawable.setStroke(4, color)

        btn_show.setOnClickListener {
            tv_show.background = if (show) {
                gradientDrawable
            } else {
                null
            }
            tv_show.horizontalPadding = if (show) {
                8
            } else {
                0
            }
            show = !show
        }

    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}
