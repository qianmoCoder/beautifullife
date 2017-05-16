package com.ddu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ddu.ui.fragment.study.StudyContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/9.
 */

public class StudyContentFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitleList = new ArrayList<>();

    public StudyContentFragmentPagerAdapter(FragmentManager fm, List<String> titleList) {
        super(fm);
        if (null != titleList) {
            mTitleList = titleList;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return StudyContentFragment.newInstance(0);
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
