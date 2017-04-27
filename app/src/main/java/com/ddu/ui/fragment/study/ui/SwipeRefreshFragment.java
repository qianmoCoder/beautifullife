package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/5/6.
 */
public class SwipeRefreshFragment extends DefaultFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @BindView(R.id.rv_swipe_refresh)
    RecyclerView mRvSwipeRefresh;
    @Nullable
    @BindView(R.id.srl_swipe_refresh)
    SwipeRefreshLayout mSrlSwipeRefresh;

    @Nullable
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    @NonNull
    private List<String> mDatas = new ArrayList<>();

    private DefaultRecycleViewAdapter<String> mAdapter;

    private Unbinder unbinder;

    @NonNull
    public static SwipeRefreshFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString("ARGUMENT_TASK_ID", taskId);
        SwipeRefreshFragment fragment = new SwipeRefreshFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 10; i++) {
            mDatas.add("i - " + i);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_swipe_refresh;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);

        mSrlSwipeRefresh.setOnRefreshListener(this);
        mSrlSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mRvSwipeRefresh.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DefaultRecycleViewAdapter<String>(mContext, mDatas) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.recyclerview_item_default;
            }

            @Override
            public void bindView(@NonNull ViewHolder viewHolder, String data, int position) {
                viewHolder.setText(R.id.tv_detail, data);
            }
        };

        mRvSwipeRefresh.setAdapter(mAdapter);

        mAdapter.setEmptyView(R.layout.empty_view, mRvSwipeRefresh);

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        App.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = mDatas.size();
                for (int i = size; i < size + 10; i++) {
                    mDatas.add("i - " + i);
                }
                mAdapter.notifyDataSetChanged();
                mSrlSwipeRefresh.setRefreshing(false);
            }
        }, 2000);

    }
}
