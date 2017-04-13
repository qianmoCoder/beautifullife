package com.ddu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.view.OptionItemView;
import com.ddu.icore.util.sys.ViewUtils;
import com.ddu.ui.fragment.work.FragmentA;

/**
 * Created by yzbzz on 16/4/6.
 */
public class WorkFragment extends DefaultFragment {

    private OptionItemView mOptionItemView;

    @NonNull
    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    public void initView() {
        mOptionItemView = ViewUtils.findViewById(mView, R.id.oiv_fragment);
        mOptionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("type", FragmentUtils.FRAGMENT_ADD);
                startFragment(FragmentA.class);
//                startActivity(new Intent(mContext, LoginActivity.class));
//                mActivity.overridePendingTransition(R.anim.bottom_view_anim_enter,R.anim.bottom_view_anim_exit);
            }
        });
        setTitle(R.string.main_tab_work);
    }
}
