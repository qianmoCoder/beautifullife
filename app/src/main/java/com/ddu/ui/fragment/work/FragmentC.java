package com.ddu.ui.fragment.work;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.sys.ViewUtils;

/**
 * Created by lhz on 16/4/6.
 */
public class FragmentC extends DefaultFragment {

    private Button mBtnOk;

    @NonNull
    public static FragmentC newInstance() {
        FragmentC fragment = new FragmentC();
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
                //FragmentManager.POP_BACK_STACK_INCLUSIVE
//                String tag = FragmentA.class.getName();
//
//                int size = getFragmentManager().getBackStackEntryCount();
//                for (int i = 0; i < size; i++) {
//                    FragmentManager.BackStackEntry backStackEntry = getFragmentManager().getBackStackEntryAt(i);
//
//                }
//
//
//                boolean rst = getFragmentManager().popBackStackImmediate(tag, 0);
            }
        });
        setDefaultTitle("FragmentC");
    }

}
