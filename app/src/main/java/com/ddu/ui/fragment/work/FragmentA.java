package com.ddu.ui.fragment.work;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.activity.ShowDetailActivity;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.FragmentUtils;
import com.ddu.icore.util.sys.ViewUtils;

import butterknife.OnClick;

/**
 * Created by lhz on 16/4/6.
 */
public class FragmentA extends DefaultFragment {

    private Button mBtnOk;

    @NonNull
    public static FragmentA newInstance() {
        FragmentA fragment = new FragmentA();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_state;
    }

    @Override
    public void initView() {
        mBtnOk = ViewUtils.findViewById(mView, R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseActivity instanceof ShowDetailActivity) {
                    ShowDetailActivity activity = (ShowDetailActivity) baseActivity;
                    FragmentB f = FragmentB.newInstance();
                    activity.replaceFragment(f, FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK);
                }

            }
        });
        setDefaultTitle("FragmentA");
    }

    @OnClick(R.id.rl_person_info)
    public void onClick() {

    }
}
