package com.ddu.ui.fragment.life;

import android.os.Bundle;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;

/**
 * Created by yzbzz on 2016/10/27.
 */

public class FoodFragment extends DefaultFragment {
    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_life_food;
    }

    @Override
    public void initView() {
        setDefaultTitle("健康小知识");
    }
}
