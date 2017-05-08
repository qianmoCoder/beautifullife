package com.ddu.ui.view;

import android.content.Context;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.internal.LoadingView;

/**
 * Created by yzbzz on 2017/5/8.
 */

public class CustomerView extends LoadingView {

    public CustomerView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase pullToRefreshBase) {
        super(context, mode, pullToRefreshBase.getPullToRefreshScrollDirection());
    }


    @Override
    public View getLoadingView() {
        return mLayoutInflater.inflate(R.layout.fragment_refresh, this);
    }
}
