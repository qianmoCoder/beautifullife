package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.widget.ScrollView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.view.CustomerScrollView;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class ScrollViewFragment extends DefaultFragment implements CustomerScrollView.ScrollViewListener {

    private PullToRefreshScrollView customerScrollView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scroll_view;
    }

    @Override
    public void initView() {
        customerScrollView = findViewById(R.id.csv_activity_base);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
    }
}
