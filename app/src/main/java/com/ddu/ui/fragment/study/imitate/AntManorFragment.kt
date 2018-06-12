package com.ddu.ui.fragment.study.imitate

import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_study_imitate_ant_manor.*

/**
 * Created by yzbzz on 2018/6/7.
 */
@Element("UI")
class AntManorFragment : DefaultFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_study_imitate_ant_manor
    }

    override fun initView() {
        btn_press.setOnClickListener {
            ll_love.addLoveView()
        }
    }
}
