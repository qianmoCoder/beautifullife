package com.ddu.ui.fragment.work

import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_work_program_kotlin.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by yzbzz on 2019/1/9.
 */
class KotlinFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_work_program_kotlin
    }

    override fun initView() {
        btn_click.setOnClickListener {
            GlobalScope.launch {

            }
        }
    }
}
