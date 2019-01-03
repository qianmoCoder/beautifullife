package com.ddu.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ddu.R;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhengjingle on 2016/12/29.
 */

public class WuBaBehavior extends CoordinatorLayout.Behavior<View>{

    Activity act;
    View dependency;
    FrameLayout mFrameLayout;
    LinearLayout ll_search;
    RecyclerView mRecyclerView;

    LinearLayout ll_weather;

    public WuBaBehavior(Context context) {
        super();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.dependency) {
            this.dependency = parent.findViewById(R.id.dependency);
            mFrameLayout = (FrameLayout) parent.findViewById(R.id.frameLayout);
            ll_search = (LinearLayout) parent.findViewById(R.id.ll_search);
            mRecyclerView = (RecyclerView) parent.findViewById(R.id.recyclerView);

            ll_weather = (LinearLayout) parent.findViewById(R.id.ll_weather);

            act = (Activity) dependency.getContext();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float dependencyTranslationY = dependency.getTranslationY();

        //weather
        float weatherProcess = dependencyTranslationY / getDependentExpanded();
        if (weatherProcess < 0) weatherProcess = 0;
        if (weatherProcess > 1) weatherProcess = 1;
        ll_weather.setAlpha(weatherProcess);

        //ll_search
        dependencyTranslationY = dependency.getTranslationY();
        if (dependencyTranslationY > getDependentCollapsed()) {
            if (dependencyTranslationY > getDependentExpanded())
                dependencyTranslationY = getDependentExpanded();

            float searchProcess = (dependencyTranslationY - getDependentCollapsed()) / (getDependentExpanded() - getDependentCollapsed());

            CoordinatorLayout.LayoutParams layoutParams = ((CoordinatorLayout.LayoutParams) ll_search.getLayoutParams());
            float rightMargin = (getSearchRightMarginExpanded() - getSearchRightMarginCollapsed()) * searchProcess + getSearchRightMarginCollapsed();
            layoutParams.rightMargin = (int) rightMargin;
            ll_search.setLayoutParams(layoutParams);

            searchProcess = (1 - 0.3f) * searchProcess + 0.3f;
            ll_search.setAlpha(searchProcess);
        }


        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {

    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0) {
            float newTranslationY = dependency.getTranslationY() - dy;
            float minY = getDependentCollapsed();
            if (newTranslationY > minY) {
                consumed[1] = dy;
                move(newTranslationY);
            }

        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyUnconsumed < 0) {
            float newTranslationY = dependency.getTranslationY() - dyUnconsumed;
            float maxY = getDependentMaxRefresh();

            if (newTranslationY < maxY) {
                move(newTranslationY);
            }
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return actionUp(velocityY);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
    }

    private boolean actionUp(float velocity) {
        float dependencyTranslationY = dependency.getTranslationY();

        //在折叠或展开的位置不发生动画滚动
        if (dependencyTranslationY == getDependentCollapsed() || dependencyTranslationY == getDependentExpanded()) {
            return false;
        }


        //分情况判断怎么动画滚动
        float middleOfCollapsedExpanded = (getDependentCollapsed() + getDependentExpanded()) / 2;
        if (dependencyTranslationY < middleOfCollapsedExpanded) {//折叠
        } else if (dependencyTranslationY < getDependentExpanded()) {//展开
        } else if (dependencyTranslationY < getDependentRefresh()) {//展开
        }  else {
            return false;
        }

        return true;
    }


    private void move(float y) {
        dependency.setTranslationY(y);
        mFrameLayout.setTranslationY(y);
        ll_search.setTranslationY(y);
        mRecyclerView.setTranslationY(y);

    }

    private float getDependentCollapsed() {
        return dependency.getResources().getDimension(R.dimen.dp_0);
    }

    private float getDependentExpanded() {
        return dependency.getResources().getDimension(R.dimen.dp_60);
    }

    private float getDependentRefresh() {
        return dependency.getResources().getDimension(R.dimen.dp_150);
    }

    private float getDependentMaxRefresh() {
        return dependency.getResources().getDimension(R.dimen.dp_130);
    }

    private float getSearchRightMarginCollapsed() {
        return dependency.getResources().getDimension(R.dimen.dp_60);
    }

    private float getSearchRightMarginExpanded() {
        return dependency.getResources().getDimension(R.dimen.dp_20);
    }
}
