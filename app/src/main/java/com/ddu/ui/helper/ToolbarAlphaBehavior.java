package com.ddu.ui.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.ddu.R;

/**
 * Created by yzbzz on 16/5/6.
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    private int pixelOffset;
    private int height;

    public ToolbarAlphaBehavior(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        pixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        height = pixelOffset - child.getHeight();
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        offset = target.getScrollY();

//        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150) - toolbar.getHeight();
////        offset += dyConsumed;
        if (offset <= 20) {  //alpha为0
            child.getBackground().setAlpha(0);
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
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    private int temp;
    private int tempy;

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull Toolbar toolbar, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset = target.getScrollY();

//        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150) - toolbar.getHeight();
////        offset += dyConsumed;
        temp += dyConsumed;
        tempy += dyUnconsumed;
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
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
