package com.ddu.ui.fragment.study.ui

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.AnimatorUtils
import com.ddu.ui.fragment.study.customer.ShapeInjectFragment
import com.google.android.material.tabs.TabLayout
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_design.*

/**
 * Created by lhz on 16/5/6.
 */
@IElement("UI")
class PorterDuffXfermodeFragment : DefaultFragment() {


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_round_rect
    }

    override fun initView() {

    }

    companion object {

        fun newInstance(taskId: String): PorterDuffXfermodeFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = PorterDuffXfermodeFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
