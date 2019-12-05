package com.ddu.ui.fragment.study.jetpack

import com.ddu.help.LifeCycleHandler
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement


/**
 * Created by yzbzz on 2019/1/24.
 */
@IElement("Jetpack")
class HandleFragment : DefaultFragment() {

    override fun getLayoutId() = com.ddu.R.layout.fragment_jetpack_paging

    private lateinit var myHandler: LifeCycleHandler

    override fun initView() {
        myHandler = LifeCycleHandler(this) {
            if (it.what == 1) {
                myHandler.sendEmptyMessageDelayed(1, 1000)
            }
        }

        myHandler.sendEmptyMessageDelayed(1, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}