package com.ddu.ui.fragment.work;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.activity.ShowDetailActivity;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.view.CustomerTimeLineMarker;
import com.ddu.icore.util.FragmentUtils;
import com.ddu.icore.util.sys.ViewUtils;

import butterknife.OnClick;

/**
 * Created by lhz on 16/4/6.
 */
public class FragmentA extends DefaultFragment {

    private Button mBtnOk;
    private Button button;

    private CustomerTimeLineMarker item_time_line_mark;


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
        button = ViewUtils.findViewById(mView, R.id.button);
        item_time_line_mark = ViewUtils.findViewById(mView, R.id.item_time_line_mark);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareDialogFragment bottomSheetDialogFragment = new ShareDialogFragment();
//                bottomSheetDialogFragment.show(getFragmentManager(), "");
//                replaceFragment(FragmentB.newInstance());
                ((ShowDetailActivity) mActivity).replaceFragment(FragmentB.newInstance(), FragmentUtils.FRAGMENT_ADD_TO_BACK_STACK);
            }
        });
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentB f = FragmentB.newInstance();
//                replaceFragment(f);
                item_time_line_mark.setCount(2);

            }
        });
        setDefaultTitle("FragmentA");
    }

    @OnClick(R.id.rl_person_info)
    public void onClick() {

    }
}
