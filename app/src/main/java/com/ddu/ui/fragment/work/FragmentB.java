package com.ddu.ui.fragment.work;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.sys.ViewUtils;

/**
 * Created by lhz on 16/4/6.
 */
public class FragmentB extends DefaultFragment {

    private Button mBtnOk;

    @NonNull
    public static FragmentB newInstance() {
        FragmentB fragment = new FragmentB();
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
        mBtnOk = ViewUtils.findViewById(getMView(), R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentC f = FragmentC.newInstance();
//                replaceFragment(f);
            }
        });
        setDefaultTitle("FragmentB");
    }

}
