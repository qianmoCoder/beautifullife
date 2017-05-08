package com.ddu.icore.refresh.internal;

import android.view.View;
import android.view.ViewGroup;

import com.ddu.icore.refresh.PullToRefreshBase;


/**
 * Created by yzbzz on 2017/5/5.
 */

public abstract class LoadingView {

    private LoadingLayout mView;

    public LoadingView() {
    }

    public final int getContentSize(PullToRefreshBase.Orientation direction) {
        int size;
        switch (direction) {
            case HORIZONTAL:
                size = mView.getWidth();
                break;
            case VERTICAL:
                size = mView.getHeight();
                break;
            default:
                size = mView.getHeight();
                break;
        }
        return size;
    }


    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        lp.height = height;
        mView.requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = mView.getLayoutParams();
        lp.width = width;
        mView.requestLayout();
    }

    public void setView(LoadingLayout view) {
        this.mView = view;
    }

    public View getView() {
        return mView;
    }


    public void onPull(float scale) {
        mView.onPull(scale);
    }

    public void refreshing() {
        mView.refreshing();
    }

    public void releaseToRefresh() {
        mView.releaseToRefresh();
    }

    public void reset() {
        mView.reset();
    }

    public void pullToRefresh() {
        mView.releaseToRefresh();
    }

}
