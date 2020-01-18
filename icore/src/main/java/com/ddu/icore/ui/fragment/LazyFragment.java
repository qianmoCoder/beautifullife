package com.ddu.icore.ui.fragment;

/**
 * Created by yzbzz on 2016/11/4.
 */

public abstract class LazyFragment extends BaseFragment {

    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            getData();
            isFirstLoad = false;
        }
    }

    public void getData() {

    }
}
