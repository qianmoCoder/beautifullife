package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.R;

/**
 * Created by yzbzz on 16/5/6.
 */
public class ToolBarFragment extends DefaultFragment {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_toolbar;
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
