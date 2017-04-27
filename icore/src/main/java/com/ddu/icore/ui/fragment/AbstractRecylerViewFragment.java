package com.ddu.icore.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ddu.icore.R;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.util.sys.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/4/19.
 */

public abstract class AbstractRecylerViewFragment<D, A extends DefaultRecycleViewAdapter> extends DefaultFragment {

    protected RecyclerView mRvDefault;
    protected LinearLayoutManager mLinearLayoutManager;

    protected List<D> mDataEntities = new ArrayList<>();

    protected A mAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycle_view_default;
    }

    @Override
    public void initView() {
        mRvDefault = ViewUtils.findViewById(mView, R.id.rl_default);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvDefault.setLayoutManager(mLinearLayoutManager);
        mRvDefault.setHasFixedSize(true);
        mRvDefault.setNestedScrollingEnabled(false);

        mAdapter = getAdapter();
        mRvDefault.setAdapter(mAdapter);
    }

    public abstract A getAdapter();
}
