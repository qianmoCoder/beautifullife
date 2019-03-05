package com.ddu.ui.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yzbzz on 16/5/6.
 */
public class ScrollBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = "ToolbarAlphaBehavior";

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        int leftScrolled = target.getScrollY();
        child.setScrollY(leftScrolled);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, @NonNull View child, View target, float velocityX, float velocityY, boolean consumed) {
        ((NestedScrollView) child).fling((int) velocityY);
        return true;
    }
}
