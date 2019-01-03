package com.ddu.ui.fragment.life

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_life_mortgage.*

/**
 * Created by yzbzz on 16/4/21.
 */
class MortgageFragment : DefaultFragment() {


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life_mortgage
    }

    override fun initView() {
        val adapter = SampleFragmentPagerAdapter(baseActivity.supportFragmentManager, baseActivity)
        findViewById<ViewPager>(R.id.vp_life_mortgage).adapter = adapter
        tl_life_mortgage.setupWithViewPager(findViewById(R.id.vp_life_mortgage))
        findViewById<ViewPager>(R.id.vp_life_mortgage).currentItem = 0
        setDefaultTitle("房贷计算器")
    }


    inner class SampleFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
        val PAGE_COUNT = 0
        private val tabTitles = arrayOf("缴费", "入场", "更多")

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return LoanFragment()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

}
