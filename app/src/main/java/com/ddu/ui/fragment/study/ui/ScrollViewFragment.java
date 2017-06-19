package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.refresh.internal.RotateLoadingLayout;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.view.CustomerScrollView;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class ScrollViewFragment extends DefaultFragment implements CustomerScrollView.ScrollViewListener {

    private PullToRefreshScrollView customerScrollView;
    private FrameLayout frameLayout;
    private RotateLoadingLayout rotateLoadingLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scroll_view;
    }

    @Override
    public void initView() {
        frameLayout = findViewById(R.id.fl_refresh_content);
        customerScrollView = findViewById(R.id.csv_activity_base);
        rotateLoadingLayout = new RotateLoadingLayout(mContext, PullToRefreshBase.Mode.PULL_FROM_START, customerScrollView.getPullToRefreshScrollDirection());
        frameLayout.addView(rotateLoadingLayout);
        customerScrollView.setRefreshView(rotateLoadingLayout);
        customerScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                customerScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customerScrollView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                customerScrollView.onRefreshComplete();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
    }
}
