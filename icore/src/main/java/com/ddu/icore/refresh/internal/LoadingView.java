package com.ddu.icore.refresh.internal;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ddu.icore.R;
import com.ddu.icore.refresh.PullToRefreshBase;


/**
 * Created by yzbzz on 2017/5/5.
 */

public abstract class LoadingView extends FrameLayout implements ILoadingLayout {

    protected View mRootView;
    protected View mInnerLayout;

    protected LayoutInflater mLayoutInflater;

    public LoadingView(Context context) {
        super(context);
        mLayoutInflater = LayoutInflater.from(context);
        mRootView = getLoadingView();
        mInnerLayout = findViewById(R.id.refresh_loading_view);
    }

    public LoadingView(Context context, final PullToRefreshBase.Mode mode, final PullToRefreshBase.Orientation scrollDirection) {
        this(context);
        if (null != mInnerLayout) {
            LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
            switch (mode) {
                case PULL_FROM_END:
                    lp.gravity = scrollDirection == PullToRefreshBase.Orientation.VERTICAL ? Gravity.TOP : Gravity.LEFT;
                    break;
                case PULL_FROM_START:
                default:
                    lp.gravity = scrollDirection == PullToRefreshBase.Orientation.VERTICAL ? Gravity.BOTTOM : Gravity.RIGHT;
                    break;
            }
        }
    }

    public int getContentSize(PullToRefreshBase.Orientation direction) {
        int size = -1;
        if (null == mInnerLayout) {
            return size;
        }
        switch (direction) {
            case HORIZONTAL:
                size = mInnerLayout.getWidth();
                break;
            case VERTICAL:
            default:
                size = mInnerLayout.getHeight();
                break;
        }
        return size;
    }

    public final void setHeight(int height) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = height;
        requestLayout();
    }

    public final void setWidth(int width) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = width;
        requestLayout();
    }


    public void onPull(float scale) {
    }

    public void refreshing() {
    }

    public void releaseToRefresh() {
    }

    public void reset() {
    }

    public void pullToRefresh() {
    }

    public abstract View getLoadingView();

}
