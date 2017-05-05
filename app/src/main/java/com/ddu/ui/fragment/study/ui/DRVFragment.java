package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.AbstractRecycleViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/5.
 */

public class DRVFragment extends AbstractRecycleViewFragment implements PullToRefreshBase.OnRefreshListener2 {

    private List<String> mDatas = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 50; i++) {
            mDatas.add("i - " + i);
        }
    }

    @Override
    public DefaultRecycleViewAdapter getAdapter() {
        return new DefaultRecycleViewAdapter<String>(mContext, mDatas) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.recyclerview_item_default;
            }

            @Override
            public void bindView(final ViewHolder viewHolder, String data, int position) {
                viewHolder.setText(R.id.tv_detail, data);
            }
        };
    }

    @Override
    public void initRefreshView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefreshScrollView.setOnRefreshListener(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        App.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = mDatas.size();
                for (int i = size; i < size + 10; i++) {
                    mDatas.add(0, "i - s - " + i);
                }
                mAdapter.notifyDataSetChanged();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        App.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = mDatas.size();
                for (int i = size; i < size + 10; i++) {
                    mDatas.add("i - " + i);
                }
                mAdapter.notifyDataSetChanged();
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 1000);
    }
}
