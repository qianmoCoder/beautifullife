package com.ddu.icore.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ddu.icore.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.IObserver;
import com.ddu.icore.common.ObserverManager;


public abstract class BaseFragment extends Fragment implements IObserver<GodIntent> {

    @Nullable
    protected View mRootView;

    protected Activity mActivity;
    protected Context mContext;
    private FrameLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        initData(savedInstanceState);
//        Log.v("lhz", getClass().getName() + " onCreate ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
//        Log.v("lhz", getClass().getName() + " onAttach ");
    }


    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.v("lhz", getClass().getName() + " onCreateView");
        if (null == mRootView) {
            if (getUserVisibleHint()) {
                onDataLoad();
            } else {
                layout = new FrameLayout(getContext());
                layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_lazy_loading, null);
                layout.addView(view);
            }
            mRootView = getContentView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Log.v("lhz", getClass().getName() + " onViewCreated ");
    }

    public void initData(Bundle savedInstanceState) {

    }

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

    public void onDataLoad() {
//        Log.v("lhz", getClass().getName() + " onDataLoad");
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.v("lhz", getClass().getName() + " hidden: " + hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.v("lhz", getClass().getName() + " onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.v("lhz", getClass().getName() + " onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.v("lhz", getClass().getName() + " onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.v("lhz", getClass().getName() + " onPause");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.v("lhz", getClass().getName() + " setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
//        Log.v("lhz", getClass().getName() + " onAttachFragment");
    }


}
