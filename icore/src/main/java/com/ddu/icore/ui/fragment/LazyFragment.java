package com.ddu.icore.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yzbzz on 2016/11/4.
 */

public class LazyFragment extends BaseFragment {

    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";

    private boolean isInit = false;
    private boolean isLazyLoad = true;
    private Bundle mSavedInstanceState;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD);
        }
        if (isLazyLoad) {
            if (getUserVisibleHint() && !isInit) {
                mSavedInstanceState = savedInstanceState;

            }
        }
        return null;
    }


}
