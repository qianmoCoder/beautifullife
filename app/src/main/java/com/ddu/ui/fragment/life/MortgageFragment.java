package com.ddu.ui.fragment.life;

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
 * Created by yzbzz on 16/4/21.
 */
public class MortgageFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.tl_life_mortgage)
    TabLayout tlLifeMortgage;
    @Nullable
    @BindView(R.id.vp_life_mortgage)
    ViewPager vpLifeMortgage;

    private Unbinder unbinder;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life_mortgage;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(baseActivity.getSupportFragmentManager(),baseActivity);
        vpLifeMortgage.setAdapter(adapter);
        tlLifeMortgage.setupWithViewPager(vpLifeMortgage);
        vpLifeMortgage.setCurrentItem(0);
        setDefaultTitle("房贷计算器");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 0;
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
            return new LoanFragment();
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

}
