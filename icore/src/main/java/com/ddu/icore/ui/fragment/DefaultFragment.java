package com.ddu.icore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddu.icore.R;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.icore.ui.view.TitleBar;

public abstract class DefaultFragment extends BaseFragment {

    public static final String ARGUMENT_TASK_ID = "ARGUMENT_TASK_ID";

    protected BaseActivity baseActivity;
    protected View mView;
    protected TitleBar mTitleBar;
    protected View mCustomerTitleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) mActivity;
    }


    @Override
    public View getContentView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (isShowTitleBar()) {
            mView = inflater.inflate(R.layout.activity_base, container, false);

//            mTitleBar = (TitleBar) mView.findViewById(R.id.ll_title_bar);
            setTitleBar();

            inflater.inflate(getLayoutId(), (ViewGroup) mView, true);
        } else {
            mView = inflater.inflate(getLayoutId(), container, false);
        }
        initView();
        return mView;
    }

    public int setTitleBarResId() {
        return R.layout.widget_title_bar;
    }

    private void setTitleBar() {
        mCustomerTitleBar = LayoutInflater.from(mContext).inflate(setTitleBarResId(), (ViewGroup) mView);
        mTitleBar = (TitleBar) mCustomerTitleBar.findViewById(R.id.ll_title_bar);
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public void startFragment(@NonNull Class className) {
        baseActivity.startFragment(className);
    }

    public void startFragment(@NonNull Class className, Bundle bundle) {
        baseActivity.startFragment(className, bundle);
    }

    public void setTitle(int resId) {
        if (null != mTitleBar) {
            mTitleBar.setMiddleText(resId);
        }
    }

    public void setTitle(String title) {
        if (null != mTitleBar) {
            mTitleBar.setMiddleText(title);
        }
    }

    public void setDefaultTitle(int resId) {
        if (null != mTitleBar) {
            mTitleBar.setDefaultTitle(resId, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.onBackPressed();
                }
            });
        }
    }

    public void setDefaultTitle(String title) {
        if (null != mTitleBar) {
            mTitleBar.setDefaultTitle(title, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseActivity.onBackPressed();
                }
            });
        }
    }

    public void setRightText(String text, View.OnClickListener onClickListener) {
        if (null != mTitleBar) {
            mTitleBar.setRightText(text, onClickListener);
        }
    }

    public void setRightImg(int resId, View.OnClickListener onClickListener) {
        if (null != mTitleBar) {
            mTitleBar.setRightImg(resId, onClickListener);
        }
    }

    public void setTitleBarOnClickListener(View.OnClickListener onClickListener) {
        if (null != mTitleBar) {
            mTitleBar.setOnClickListener(onClickListener);
        }
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

}
