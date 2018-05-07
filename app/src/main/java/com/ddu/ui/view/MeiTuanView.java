package com.ddu.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.internal.LoadingView;

/**
 * Created by yzbzz on 2017/5/8.
 */

public class MeiTuanView extends LoadingView {

    private ImageView loading;
    private int viewHeight;
    private float pull_distance = 0;

    public MeiTuanView(Context context, PullToRefreshBase pullToRefreshBase) {
        super(context, pullToRefreshBase.getMode(), pullToRefreshBase.getPullToRefreshScrollDirection());
        loading = (ImageView) mRootView.findViewById(R.id.loading);
        measureView(mRootView);
        viewHeight = mRootView.getMeasuredHeight();
    }

    @Override
    public View getLoadingView() {
        return mLayoutInflater.inflate(R.layout.meituan_header_refresh_layout, this);
    }

    @Override
    public void onPull(float scale) {
        pull_distance = pull_distance + scale;
        float scale1 = pull_distance / viewHeight;
        loading.setScaleX(scale1);
        loading.setScaleY(scale1);
    }

    @Override
    public void releaseToRefresh() {
        loading.setImageResource(R.drawable.pull_image);
        loading.setScaleX(0);
        loading.setScaleY(0);
        pull_distance = 0;
    }

    public void pullToRefresh() {
        loading.setImageResource(R.drawable.mei_tuan_loading_pre);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    public void refreshing() {
        loading.setImageResource(R.drawable.mei_tuan_loading);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) loading.getDrawable();
        mAnimationDrawable.start();
    }

    public void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
}
