package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.Element;

/**
 * Created by yzbzz on 16/5/6.
 */
@Element("UI")
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
        Toolbar toolbar = (Toolbar) getMView().findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
