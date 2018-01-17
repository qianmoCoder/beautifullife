package com.ddu.icore.aspect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by yzbzz on 2018/1/16.
 */

public class MonitorCallback implements Window.Callback {

    private Window.Callback mCallback;
    private Activity mAcitivty;

    MonitorCallback(Activity activity, Window.Callback callback) {
        this.mCallback = callback;
        this.mAcitivty = activity;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mCallback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return mCallback.dispatchKeyShortcutEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mCallback.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return mCallback.dispatchTrackballEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return mCallback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return mCallback.dispatchPopulateAccessibilityEvent(event);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        return mCallback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return mCallback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return mCallback.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return mCallback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return mCallback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        mCallback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        mCallback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mCallback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        mCallback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        mCallback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        mCallback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return mCallback.onSearchRequested();
    }

    @TargetApi(23)
    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return mCallback.onSearchRequested(searchEvent);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return mCallback.onWindowStartingActionMode(callback);
    }

    @TargetApi(23)
    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return mCallback.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        mCallback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        mCallback.onActionModeFinished(mode);
    }
}
