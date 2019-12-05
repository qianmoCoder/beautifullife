package com.ddu.ui.fragment.person

import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement

/**
 * Created by yzbzz on 16/4/11.
 */
@IElement("UI")
class PersonalInfoFragment : DefaultFragment() {


    private var mVerticalOffset = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_person_info
    }

    override fun initView() {
        initInstances()
//        appbar.addOnOffsetChangedListener { _, verticalOffset ->
//            mVerticalOffset = verticalOffset
//            if (mVerticalOffset == 0) {
//                ptf!!.setCanScroll(true)
//            } else {
//                ptf!!.setCanScroll(false)
//            }
//        }

        //        PtrHandler ptrHandler = new PtrHandler() {
        //
        //            @Override
        //            public boolean checkCanDoRefresh(ptf frame, View content, View header) {
        //                return mVerticalOffset == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
        //            }
        //
        //            @Override
        //            public void onRefreshBegin(ptf frame) {
        //                ptf.postDelayed(new Runnable() {
        //                    @Override
        //                    public void run() {
        //                        ptf.refreshComplete();
        //                    }
        //                }, 2000);
        //            }
        //        };
        //        ptf.setPtrHandler(ptrHandler);
        //        ptf.addPtrUIHandler(new PtrUIHandler() {
        //            @Override
        //            public void onUIReset(ptf frame) {
        //            }
        //
        //            @Override
        //            public void onUIRefreshPrepare(ptf frame) {
        //            }
        //
        //            @Override
        //            public void onUIRefreshBegin(ptf frame) {
        //            }
        //
        //            @Override
        //            public void onUIRefreshComplete(ptf frame) {
        //            }
        //
        //            @Override
        //            public void onUIPositionChange(ptf frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //            }
        //        });
    }

    private fun initInstances() {
//        baseActivity.setSupportActionBar(toolbar)

        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
        //        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

}
