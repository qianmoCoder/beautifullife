package com.ddu.ui.fragment.study.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class DesignFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.tl_design)
    TabLayout tlLifeMortgage;
    @Nullable
    @BindView(R.id.vp_design)
    ViewPager vpLifeMortgage;

    private Unbinder unbinder;

    @NonNull
    public static DesignFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString("ARGUMENT_TASK_ID", taskId);
        DesignFragment fragment = new DesignFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_design;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(baseActivity.getSupportFragmentManager(), baseActivity);
        vpLifeMortgage.setAdapter(adapter);
        tlLifeMortgage.setupWithViewPager(vpLifeMortgage);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        @NonNull
        private String tabTitles[] = new String[]{"缴费", "入场", "更多"};
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
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
