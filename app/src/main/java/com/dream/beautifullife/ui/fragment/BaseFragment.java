package com.dream.beautifullife.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dream.beautifullife.ui.activity.BaseActivity;

/**
 * Created by admin on 2015/9/24.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity)getActivity();
        initData();
    }

    public abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getContentView(inflater,container,savedInstanceState);
        return view;
    }

    public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
