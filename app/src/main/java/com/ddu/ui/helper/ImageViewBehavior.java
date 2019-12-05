package com.ddu.ui.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.ddu.R;

/**
 * Created by yzbzz on 16/5/6.
 */
public class ImageViewBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;

    private int pixelOffset;
    private int height;

    private int startX;
    private int startY;

    private int endX;
    private int endY;

    public ImageViewBehavior(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        pixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, ImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, @NonNull ImageView child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        height = pixelOffset - child.getHeight();
        startX = (int) child.getX();
        startY = (int) child.getY();

        endX = 24;
        endY = 50;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        offset = target.getScrollY();
//        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150) - toolbar.getHeight();
////        offset += dyConsumed;
        if (offset <= 20) {  //alpha为0
            child.setX(endX);
            child.setY(endY);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = 24;
            lp.height = 24;
            child.setLayoutParams(lp);
        } else {  //alpha为255
            int alpha = pixelOffset - offset;
            if (offset >= height) {
                child.getBackground().setAlpha(255);
            } else {
                child.getBackground().setAlpha(alpha);
            }
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, ImageView child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull ImageView toolbar, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset = target.getScrollY();
        if (offset <= 20) {  //alpha为0
            toolbar.getBackground().setAlpha(0);
        } else {  //alpha为255
            int alpha = pixelOffset - offset;
            if (offset >= height) {
                toolbar.getBackground().setAlpha(255);
            } else {
                toolbar.getBackground().setAlpha(alpha);
            }
        }

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, ImageView child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, ImageView child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
