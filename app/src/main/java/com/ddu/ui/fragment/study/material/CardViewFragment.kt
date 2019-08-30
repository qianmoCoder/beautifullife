package com.ddu.ui.fragment.study.material

import android.os.Bundle

import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement

/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("MD")
class CardViewFragment : DefaultFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_study_md_card_view
    }

    override fun initView() {

    }

    companion object {

        fun newInstance(taskId: String): CardViewFragment {
            val arguments = Bundle()
            arguments.putString(ARGUMENT_TASK_ID, taskId)
            val fragment = CardViewFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
