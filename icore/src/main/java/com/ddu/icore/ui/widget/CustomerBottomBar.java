package com.ddu.icore.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ddu.icore.R;


/**
 * Created by yzbzz on 2018/4/17.
 */
public class CustomerBottomBar {

    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;

    private final ViewGroup mTargetParent;
    private final Context mContext;

    private final AccessibilityManager mAccessibilityManager;

    final LinearLayout mView;

    private CustomerBottomBar(View view) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. "
                    + "Please provide a valid view.");
        }
        mContext = parent.getContext();
        mTargetParent = parent;
        mAccessibilityManager = (AccessibilityManager)
                mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);

        mView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.bottom_search_bar, mTargetParent, false);
        mView.findViewById(R.id.tv_title_bar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static CustomerBottomBar make(@NonNull View view) {
        return new CustomerBottomBar(view);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                } else {
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return fallback;
    }

    public void show() {
        showView();
    }

    public void dismiss() {
        hideView();
    }


    final void showView() {
        if (mView.getParent() == null) {
            mTargetParent.addView(mView);
        }

        if (shouldAnimate()) {
            animateViewIn();
        }
    }


    void animateViewIn() {
        final int viewHeight = mView.getHeight();
        mView.setTranslationY(viewHeight);
        final ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(viewHeight, 0);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int mPreviousAnimatedIntValue = viewHeight;

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                mView.setTranslationY(currentAnimatedIntValue);
                mPreviousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();

    }

    private void animateViewOut() {
        final ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, mView.getHeight());
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(ANIMATION_DURATION);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onViewHidden();
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int mPreviousAnimatedIntValue = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                mView.setTranslationY(currentAnimatedIntValue);
                mPreviousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();

    }

    final void hideView() {
        if (shouldAnimate() && mView.getVisibility() == View.VISIBLE) {
            animateViewOut();
        } else {
            onViewHidden();
        }
    }

    void onViewHidden() {
        final ViewParent parent = mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mView);
        }
    }

    /**
     * Returns true if we should animate the Snackbar view in/out.
     */
    boolean shouldAnimate() {
        return !mAccessibilityManager.isEnabled();
    }

}
