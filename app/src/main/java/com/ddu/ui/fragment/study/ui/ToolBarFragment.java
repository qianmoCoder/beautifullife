package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 16/5/6.
 */
@IElement("UI")
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
