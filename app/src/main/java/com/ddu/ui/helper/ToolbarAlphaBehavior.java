package com.ddu.ui.helper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
        Log.v("lhz", "onStartNestedScroll");
        return true;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
        Log.v("lhz", "onNestedScrollAccepted");
        height = pixelOffset - child.getHeight();
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        Log.v("lhz", "onStopNestedScroll");
        offset = target.getScrollY();
        Log.v("lhz", "stop offset: " + offset);
//        Log.v("lhz", "offset: " + offset);
//        Log.v("lhz", "dyConsumed: " + dyConsumed);
//        Log.v("lhz", "dyUnconsumed: " + dyUnconsumed);
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
//        Log.v("lhz", "onNestedPreScroll");
    }

    private int temp;
    private int tempy;

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, @NonNull Toolbar toolbar, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        offset = target.getScrollY();
//        Log.v("lhz", "offset: " + offset);
//        Log.v("lhz", "dyConsumed: " + dyConsumed);
//        Log.v("lhz", "dyUnconsumed: " + dyUnconsumed);
//        endOffset = context.getResources().getDimensionPixelOffset(R.dimen.dp_150) - toolbar.getHeight();
////        offset += dyConsumed;
        Log.v("lhz", "scroll offset: " + offset);
        Log.v("lhz", "dyConsumed: " + dyConsumed);
        Log.v("lhz", "dyUnconsumed: " + dyUnconsumed);
        temp += dyConsumed;
        tempy += dyUnconsumed;
        Log.v("lhz", "temp: " + temp);
        Log.v("lhz", "tempy: " + tempy);
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

        Log.v("lhz", "onNestedScroll");
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY) {
        Log.v("lhz", "onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, Toolbar child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.v("lhz", "onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
