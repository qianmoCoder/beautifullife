package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 16/5/6.
 */
@IElement("UI")
public class FrameLayoutFragment extends DefaultFragment {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_frame_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
