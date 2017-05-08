package com.ddu.ui.view;

import android.content.Context;
import android.view.View;

import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.refresh.internal.LoadingView;
import com.ddu.icore.refresh.internal.RotateLoadingLayout;

/**
 * Created by yzbzz on 2017/5/5.
 */

public class HeaderView extends LoadingView {

    private RotateLoadingLayout rotateLoadingLayout;
    private PullToRefreshScrollView mPullToRefreshScrollView;
    private Context mContext;

    public HeaderView(Context context, PullToRefreshScrollView pullToRefreshScrollView) {
        this.mPullToRefreshScrollView = pullToRefreshScrollView;
        mContext = context;
        getItemView();
        setView(rotateLoadingLayout);
    }

    public View getItemView() {
        rotateLoadingLayout = new RotateLoadingLayout(mContext, PullToRefreshBase.Mode.PULL_FROM_START, mPullToRefreshScrollView.getPullToRefreshScrollDirection());
        return rotateLoadingLayout;
    }
}
