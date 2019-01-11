package com.ddu.ui.fragment

import android.os.Bundle
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment

/**
 * Created by yzbzz on 16/4/6.
 */
class WorkFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_work
    }

    override fun initView() {
        setTitle(R.string.main_tab_work)
    }

    companion object {

        fun newInstance(): WorkFragment {
            val fragment = WorkFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
