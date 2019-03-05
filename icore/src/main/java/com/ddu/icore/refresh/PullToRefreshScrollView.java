package com.ddu.icore.refresh;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.ddu.icore.R;

public class PullToRefreshScrollView extends PullToRefreshBase<NestedScrollView> {

    private boolean canScroll = true;

    public PullToRefreshScrollView(Context context) {
        super(context);
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected NestedScrollView createRefreshableView(Context context, AttributeSet attrs) {
        NestedScrollView scrollView = new MyScrollView(context, attrs);
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return canScroll && mRefreshableView.getScrollY() == 0;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;
    }

    public class MyScrollView extends NestedScrollView {

        public MyScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setFillViewport(true);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            if (scrollViewListener != null) {
                scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
            }
        }
    }

    private ScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(NestedScrollView scrollView, int x, int y, int oldx, int oldy);

    }

}
