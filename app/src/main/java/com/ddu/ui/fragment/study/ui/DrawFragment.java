package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class DrawFragment extends DefaultFragment {

    private Unbinder unbinder;

    @NonNull
    public static DrawFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        DrawFragment fragment = new DrawFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_draw;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
