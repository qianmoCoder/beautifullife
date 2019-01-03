package com.ddu.ui.fragment.study.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ddu.R
import com.ddu.icore.common.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_tpi.*

/**
 * Created by lhz on 16/5/6.
 */
@IElement("UI")
class TabPageIndicatorFragment : DefaultFragment() {


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tpi
    }

    override fun initView() {
        vp_tpi.adapter = SampleFragmentPagerAdapter(fragmentManager, ctx)
//        lpi_home_card_view.setViewPager(vp_tpi)
        linepage.setViewPager(vp_tpi)
    }

    inner class SampleFragmentPagerAdapter(fm: FragmentManager?, private val context: Context) : FragmentPagerAdapter(fm) {
        private val tabTitles = arrayOf("缴费", "入场", "更多")

        override fun getCount(): Int {
            return tabTitles.size
        }

        override fun getItem(position: Int): Fragment {
            return SwipeRefreshFragment.newInstance("")
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        fun newInstance(taskId: String): TabPageIndicatorFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = TabPageIndicatorFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
