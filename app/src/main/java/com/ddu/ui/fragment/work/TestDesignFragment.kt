package com.ddu.ui.fragment.work

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_design.*
import java.util.*

/**
 * Created by lhz on 16/5/6.
 */
class TestDesignFragment : DefaultFragment() {

    private lateinit var fragmentA: FragmentA
    private lateinit var fragmentB: FragmentB
    private lateinit var fragmentC: FragmentC

    private val mList = ArrayList<Fragment>()

    override fun initData(savedInstanceState: Bundle?) {
        fragmentA = FragmentA.newInstance()
        fragmentB = FragmentB.newInstance()
        fragmentC = FragmentC.newInstance()
        mList.add(fragmentA)
        mList.add(fragmentB)
        mList.add(fragmentC)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_design
    }

    override fun initView() {
        val adapter = SampleFragmentPagerAdapter(fragmentManager!!, baseActivity)
        vp_design.setAdapter(adapter)
        tl_design.setupWithViewPager(vp_design)
        tl_design.setTabMode(TabLayout.MODE_FIXED)
    }

    inner class SampleFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
        internal val PAGE_COUNT = 3
        private val tabTitles = arrayOf("FragmentA", "FragmentB", "FragmentC")

        override fun getCount(): Int {
            return tabTitles.size
        }

        override fun getItem(position: Int): Fragment {
            return mList[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

    companion object {

        fun newInstance(taskId: String): TestDesignFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = TestDesignFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
