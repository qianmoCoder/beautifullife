package com.ddu.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ddu.ui.fragment.study.StudyContentFragment
import java.util.*

/**
 * Created by yzbzz on 2017/5/9.
 */

class StudyContentFragmentPagerAdapter(fm: FragmentManager?, titleList: List<String>?) : FragmentPagerAdapter(fm) {

    private var mTitleList: List<String> = ArrayList()

    init {
        if (null != titleList) {
            mTitleList = titleList
        }
    }

    override fun getItem(position: Int): Fragment {
        return StudyContentFragment.newInstance(0)
    }

    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }
}
