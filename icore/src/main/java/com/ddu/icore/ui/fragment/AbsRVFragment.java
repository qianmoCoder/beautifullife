package com.ddu.icore.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddu.icore.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/4/19.
 */

public abstract class AbsRVFragment<D, A extends DefaultRVAdapter> extends DefaultFragment {

    protected PullToRefreshScrollView mPullToRefreshScrollView;

    protected TextView mTvDescription;
    protected RecyclerView mRvDefault;
    protected LinearLayoutManager mLinearLayoutManager;

    protected List<D> mDataEntities = new ArrayList<>();

    protected A mAdapter;

    protected PullToRefreshBase.Mode mMode;
    protected RecyclerView.ItemDecoration mItemDecoration;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.i_fragment_recycle_view_default;
    }

    @Override
    @CallSuper
    public void initView() {

        mPullToRefreshScrollView = findViewById(R.id.default_refresh_view);
        mTvDescription = findViewById(R.id.tv_description);

        mLinearLayoutManager = new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL, false);
        mRvDefault = findViewById(R.id.rl_default);
        mRvDefault.setLayoutManager(mLinearLayoutManager);
        mRvDefault.setHasFixedSize(true);
        mRvDefault.setNestedScrollingEnabled(false);

        mItemDecoration = getItemDecoration();

        if (null != mItemDecoration) {
            mRvDefault.addItemDecoration(mItemDecoration);
        }

        mAdapter = getAdapter();
        mRvDefault.setAdapter(mAdapter);

        initRefreshView();
        mMode = mPullToRefreshScrollView.getMode();
    }

    public void initRefreshView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getMContext(), DividerItemDecoration.VERTICAL);
    }

    public TextView getTvDescription() {
        return mTvDescription;
    }

    public abstract A getAdapter();
}
