package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2017/5/25.
 */
@IElement("UI")
public class SegmentPullToRefreshFragment extends DefaultFragment {

    private PullToRefreshScrollView pullToRefreshScrollView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_segment_pull_to_refresh;
    }

    @Override
    public void initView() {
        View view = LayoutInflater.from(getMContext()).inflate(R.layout.i_rv_item_linear, null, false);
        pullToRefreshScrollView = findViewById(R.id.psv_segment);
//        pullToRefreshScrollView.addView(view, 0);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
