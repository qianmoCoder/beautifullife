package com.ddu.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ScrollView;

import androidx.core.view.ViewCompat;

import static android.R.attr.duration;

/**
 * Created by yzbzz on 2017/5/27.
 */

public class CustomerScrollView extends ScrollView {
    private State mState = State.RESET;

    static final float FRICTION = 2.0f;

    private int mTouchSlop;
    private float mLastMotionX, mLastMotionY;
    private float mInitialMotionX, mInitialMotionY;

    private boolean mIsBeingDragged = false;
    private boolean mFilterTouchEvents = true;

    private SmoothScrollRunnable mCurrentSmoothScrollRunnable;
    private boolean mLayoutVisibilityChangesEnabled = true;
    private Interpolator mScrollAnimationInterpolator;

    public CustomerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    private ScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

    }

    @Override
    public final boolean onInterceptHoverEvent(MotionEvent event) {

        final int action = event.getAction();

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsBeingDragged = false;
            return false;
        }

        if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                // If we're refreshing, and the flag is set. Eat all MOVE events
                if (isRefreshing()) {
                    return true;
                }

                if (isReadyForPull()) {
                    final float y = event.getY(), x = event.getX();
                    final float diff, oppositeDiff, absDiff;

                    // We need to use the correct values, based on scroll
                    // direction

                    diff = y - mLastMotionY;
                    oppositeDiff = x - mLastMotionX;

                    absDiff = Math.abs(diff);

                    if (absDiff > mTouchSlop && (!mFilterTouchEvents || absDiff > Math.abs(oppositeDiff))) {
                        if (diff >= 1f && isReadyForPullStart()) {
                            mLastMotionY = y;
                            mLastMotionX = x;
                            mIsBeingDragged = true;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    mLastMotionY = mInitialMotionY = event.getY();
                    mLastMotionX = mInitialMotionX = event.getX();
                    mIsBeingDragged = false;
                }
                break;
            }
        }

        return mIsBeingDragged;
    }

    public boolean isReadyForPull() {
        return getScrollY() == 0;
    }

    protected boolean isReadyForPullStart() {
        return getScrollY() == 0;
    }

    public final boolean isRefreshing() {
        return mState == State.REFRESHING || mState == State.MANUAL_REFRESHING;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRefreshing()) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if (mIsBeingDragged) {
                    mLastMotionY = event.getY();
                    mLastMotionX = event.getX();
                    pullEvent();
                    return true;
                }
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                if (isReadyForPull()) {
                    mLastMotionY = mInitialMotionY = event.getY();
                    mLastMotionX = mInitialMotionX = event.getX();
                    return true;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (mIsBeingDragged) {
                    mIsBeingDragged = false;

                    if (mState == State.RELEASE_TO_REFRESH) {
//                        setState(State.REFRESHING, true);
                        return true;
                    }

                    // If we're already refreshing, just scroll back to the top
                    if (isRefreshing()) {
                        smoothScrollTo(0);
                        return true;
                    }

                    // If we haven't returned by here, then we're not in a state
                    // to pull, so just reset
//                    setState(State.RESET);

                    return true;
                }
                break;
            }
        }

        return false;
    }

    private void pullEvent() {
        final int newScrollValue;
        final int itemDimension;
        final float initialMotionValue, lastMotionValue;


        initialMotionValue = mInitialMotionY;
        lastMotionValue = mLastMotionY;


        newScrollValue = Math.round(Math.min(initialMotionValue - lastMotionValue, 0) / FRICTION);
//        itemDimension = getHeaderSize();
        itemDimension = 50;

        setHeaderScroll(newScrollValue);

        if (newScrollValue != 0 && !isRefreshing()) {
            float scale = Math.abs(newScrollValue) / (float) itemDimension;
//            mHeaderLayout.onPull(scale);
            if (mState != State.PULL_TO_REFRESH && itemDimension >= Math.abs(newScrollValue)) {
//                setState(State.PULL_TO_REFRESH);
            } else if (mState == State.PULL_TO_REFRESH && itemDimension < Math.abs(newScrollValue)) {
//                setState(State.RELEASE_TO_REFRESH);
            }
        }
    }

    protected final void smoothScrollTo(int scrollValue) {
        smoothScrollTo(scrollValue, 0, null);
    }

    protected final void smoothScrollTo(int scrollValue, OnSmoothScrollFinishedListener listener) {
        smoothScrollTo(scrollValue, 0, listener);
    }


    private void smoothScrollTo(int newScrollValue, long delayMillis, OnSmoothScrollFinishedListener listener) {
        if (null != mCurrentSmoothScrollRunnable) {
            mCurrentSmoothScrollRunnable.stop();
        }

        final int oldScrollValue = getScrollY();

        if (oldScrollValue != newScrollValue) {
            if (null == mScrollAnimationInterpolator) {
                // Default interpolator is a Decelerate Interpolator
                mScrollAnimationInterpolator = new DecelerateInterpolator();
            }
            mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(oldScrollValue, newScrollValue, duration, listener);

            if (delayMillis > 0) {
                postDelayed(mCurrentSmoothScrollRunnable, delayMillis);
            } else {
                post(mCurrentSmoothScrollRunnable);
            }
        }
    }

    public enum State {

        /**
         * When the UI is in a state which means that user is not interacting
         * with the Pull-to-Refresh function.
         */
        RESET(0x0),

        /**
         * When the UI is being pulled by the user, but has not been pulled far
         * enough so that it refreshes when released.
         */
        PULL_TO_REFRESH(0x1),

        /**
         * When the UI is being pulled by the user, and <strong>has</strong>
         * been pulled far enough so that it will refresh when released.
         */
        RELEASE_TO_REFRESH(0x2),

        /**
         * When the UI is currently refreshing, caused by a pull gesture.
         */
        REFRESHING(0x8),

        MANUAL_REFRESHING(0x9),

        /**
         * When the UI is currently overscrolling, caused by a fling on the
         * Refreshable View.
         */
        OVERSCROLLING(0x10);

        /**
         * Maps an int to a specific state. This is needed when saving state.
         *
         * @param stateInt - int to map a State to
         * @return State that stateInt maps to
         */
        static State mapIntToValue(final int stateInt) {
            for (State value : State.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }

            // If not, return default
            return RESET;
        }

        private int mIntValue;

        State(int intValue) {
            mIntValue = intValue;
        }

        int getIntValue() {
            return mIntValue;
        }
    }

    final class SmoothScrollRunnable implements Runnable {
        private final Interpolator mInterpolator;
        private final int mScrollToY;
        private final int mScrollFromY;
        private final long mDuration;
        private OnSmoothScrollFinishedListener mListener;

        private boolean mContinueRunning = true;
        private long mStartTime = -1;
        private int mCurrentY = -1;

        public SmoothScrollRunnable(int fromY, int toY, long duration, OnSmoothScrollFinishedListener listener) {
            mScrollFromY = fromY;
            mScrollToY = toY;
            mInterpolator = mScrollAnimationInterpolator;
            mDuration = duration;
            mListener = listener;
        }

        @Override
        public void run() {

            /**
             * Only set mStartTime if this is the first time we're starting,
             * else actually calculate the Y delta
             */
            if (mStartTime == -1) {
                mStartTime = System.currentTimeMillis();
            } else {

                /**
                 * We do do all calculations in long to reduce software float
                 * calculations. We use 1000 as it gives us good accuracy and
                 * small rounding errors
                 */
                long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / mDuration;
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

                final int deltaY = Math.round((mScrollFromY - mScrollToY)
                        * mInterpolator.getInterpolation(normalizedTime / 1000f));
                mCurrentY = mScrollFromY - deltaY;
                setHeaderScroll(mCurrentY);
            }

            // If we're not at the target Y, keep going...
            if (mContinueRunning && mScrollToY != mCurrentY) {
                ViewCompat.postOnAnimation(CustomerScrollView.this, this);
            } else {
                if (null != mListener) {
                    mListener.onSmoothScrollFinished();
                }
            }
        }

        public void stop() {
            mContinueRunning = false;
            removeCallbacks(this);
        }
    }

    interface OnSmoothScrollFinishedListener {
        void onSmoothScrollFinished();
    }

    protected final void setHeaderScroll(int value) {

        final int maximumPullScroll = getMaximumPullScroll();
        value = Math.min(maximumPullScroll, Math.max(-maximumPullScroll, value));

        if (mLayoutVisibilityChangesEnabled) {
//            if (value < 0) {
//                mHeaderLayout.setVisibility(View.VISIBLE);
//            } else {
//                mHeaderLayout.setVisibility(View.INVISIBLE);
//            }
        }

        scrollTo(0, value);
    }

    private int getMaximumPullScroll() {
        return Math.round(getHeight() / FRICTION);
    }
}
