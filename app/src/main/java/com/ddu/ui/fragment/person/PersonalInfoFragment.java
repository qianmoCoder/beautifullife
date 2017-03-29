package com.ddu.ui.fragment.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/11.
 */
public class PersonalInfoFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    private Unbinder unbinder;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_info;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        initInstances();
    }

    private void initInstances() {
        baseActivity.setSupportActionBar(toolbar);

//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
