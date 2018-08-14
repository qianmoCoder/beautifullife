package com.ddu.ui.fragment.study.ui

import android.os.Bundle
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_draw.*

/**
 * Created by yzbzz on 2017/9/1.
 */
@IElement("UI")
class ViewFragment : DefaultFragment() {
    var show = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_study_view
    }

    override fun initView() {
        btn_show.animate()

    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}
