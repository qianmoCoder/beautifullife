package com.ddu.icore.ui.activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.IObserver;
import com.ddu.icore.common.ObserverManager;
import com.ddu.icore.navigation.Navigator;
import com.ddu.icore.ui.view.TitleBar;
import com.ddu.icore.util.sys.ViewUtils;

public abstract class BaseActivity extends AppCompatActivity implements IObserver<GodIntent> {

    protected Context mContext;
    protected Application mApplication;
    protected Context mApplicationContext;

    protected ViewGroup mViewGroup;

    protected TitleBar mTitleBar;

    private OnFragmentCallback mOnFragmentCallback;

    protected LayoutInflater mLayoutInflater;

    protected View mCustomerTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mApplication = getApplication();
        mApplicationContext = getApplicationContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        registerObserver();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (isShowTitleBar()) {
            super.setContentView(R.layout.activity_base);
            mViewGroup = findView(R.id.ll_activity_base);
            mTitleBar = (TitleBar) mViewGroup.findViewById(R.id.ll_title_bar);
            getLayoutInflater().inflate(layoutResID, mViewGroup);
        } else {
            super.setContentView(layoutResID);
        }

    }

    public void startFragment(@NonNull Class fragment) {
        startFragment(fragment, null);
    }

    public void startFragment(@NonNull Class<? extends Fragment> fragment, Bundle bundle) {
        Navigator.startShowDetailActivity(this, fragment, bundle);
    }

    @NonNull
    public <T extends View> T findView(int resId) {
        return ViewUtils.findViewById(this, resId);
    }

    @Override
    public void registerObserver() {

    }

    @Override
    public void onReceiverNotify(GodIntent godIntent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ObserverManager.getInstance().unRegisterObserver(this);
    }

    public boolean isShowTitleBar() {
        return true;
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    public void setDefaultTitle(int resId) {
        if (null != mTitleBar) {
            mTitleBar.setMiddleText(resId);
            setDefaultLeftImg();
        }
    }

    public void setDefaultTitle(String title) {
        if (null != mTitleBar) {
            mTitleBar.setMiddleText(title);
            setDefaultLeftImg();
        }
    }

    public void setRightText(String text, View.OnClickListener onClickListener) {
        if (null != mTitleBar) {
            TextView textView = mTitleBar.getRightText();
            textView.setText(text);
            textView.setOnClickListener(onClickListener);
        }
    }

    public void setRightImg(int resId, View.OnClickListener onClickListener) {
        if (null != mTitleBar) {
            ImageView imageView = mTitleBar.getRightImg();
            imageView.setImageResource(resId);
            imageView.setOnClickListener(onClickListener);
        }
    }

    private void setDefaultLeftImg() {
        if (null != mTitleBar) {
            mTitleBar.setDefaultLeftImg(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public interface OnFragmentCallback {
        boolean onFragmentKeyDownPress(int keyCode, KeyEvent keyEvent);

        boolean onFragmentBackPress();
    }

    public OnFragmentCallback getFragmentCallback() {
        return mOnFragmentCallback;
    }

    public void setFragmentCallback(OnFragmentCallback mOnFragmentCallback) {
        this.mOnFragmentCallback = mOnFragmentCallback;
    }
}
