package com.ddu.icore.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.BaseObserver;
import com.ddu.icore.common.ObserverManager;


public abstract class BaseFragment extends Fragment implements BaseObserver {

    @Nullable
    protected View mRootView;

    protected Activity mActivity;
    protected Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        initData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = getContentView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    public abstract void initData(Bundle savedInstanceState);

    @Nullable
    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }

    @Override
    public void registerObserver() {

    }

    @Override
    public void onReceiverNotify(GodIntent godIntent) {

    }

    public void register(int action) {
        ObserverManager.getInstance().registerObserver(action, this);
    }

    public void unregister() {
        ObserverManager.getInstance().unRegisterObserver(this);
    }

    public boolean isAddFragmentCallback() {
        return false;
    }

    public boolean isShowTitleBar() {
        return true;
    }

    public boolean isShowActivityTitleBar() {
        return false;
    }

}
