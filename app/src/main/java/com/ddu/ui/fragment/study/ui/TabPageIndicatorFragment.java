package com.ddu.ui.fragment.study.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.indicator.TabPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class TabPageIndicatorFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.tpi)
    TabPageIndicator tpi;
    @Nullable
    @BindView(R.id.vp_tpi)
    ViewPager viewPager;

    private Unbinder unbinder;

    @NonNull
    public static TabPageIndicatorFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString("ARGUMENT_TASK_ID", taskId);
        TabPageIndicatorFragment fragment = new TabPageIndicatorFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tpi;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(), mContext));
        tpi.setViewPager(viewPager);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        @NonNull
        private String tabTitles[] = new String[]{"缴费", "入场", "更多"};
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return SwipeRefreshFragment.newInstance("");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
