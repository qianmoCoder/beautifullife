package com.ddu.ui.fragment.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshScrollView;
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
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    private Unbinder unbinder;

    private int mVerticalOffset = 0;

    @BindView(R.id.ptf)
    PullToRefreshScrollView ptrFrameLayout;

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
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mVerticalOffset = verticalOffset;
                if (mVerticalOffset == 0) {
                    ptrFrameLayout.setCanScroll(true);
                } else {
                    ptrFrameLayout.setCanScroll(false);
                }
            }
        });

//        PtrHandler ptrHandler = new PtrHandler() {
//
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return mVerticalOffset == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                ptrFrameLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ptrFrameLayout.refreshComplete();
//                    }
//                }, 2000);
//            }
//        };
//        ptrFrameLayout.setPtrHandler(ptrHandler);
//        ptrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
//            @Override
//            public void onUIReset(PtrFrameLayout frame) {
//                Log.v("lhz", "onUIReset");
//            }
//
//            @Override
//            public void onUIRefreshPrepare(PtrFrameLayout frame) {
//                Log.v("lhz", "onUIRefreshPrepare");
//            }
//
//            @Override
//            public void onUIRefreshBegin(PtrFrameLayout frame) {
//                Log.v("lhz", "onUIRefreshBegin");
//            }
//
//            @Override
//            public void onUIRefreshComplete(PtrFrameLayout frame) {
//                Log.v("lhz", "onUIRefreshComplete");
//            }
//
//            @Override
//            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
//                Log.v("lhz", "onUIPositionChange: " + ptrIndicator.getCurrentPosY() + " " + ptrIndicator.getOffsetY());
//            }
//        });
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
