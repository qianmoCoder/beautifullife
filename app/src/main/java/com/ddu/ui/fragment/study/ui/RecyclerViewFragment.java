package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.sys.DensityUtils;
import com.ddu.ui.view.DividerItemDecoration;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by yzbzz on 2017/3/31.
 */

public class RecyclerViewFragment extends DefaultFragment {

    private RecyclerView recyclerView;

    private ArrayList<Integer> localImages = new ArrayList<>();


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycle_view;
    }

    private LinearLayoutManager linearLayoutManager;

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.rv_up);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int position = 0; position < 2; position++) {
                    localImages.add(getResId("ic_test_" + position, R.drawable.class));
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }, 2000);

        recyclerView.setAdapter(new DefaultRecycleViewAdapter<Integer>(mContext, localImages) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_study_recycle_list_item;
            }

            @Override
            public void bindView(final ViewHolder viewHolder, Integer data, int position) {
                DisplayMetrics display = getResources().getDisplayMetrics();
                final int width = DensityUtils.dip2px(mContext, 20);
                int w = display.widthPixels - width;
                viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT));
                viewHolder.setImageResource(R.id.iv_up, data);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean mScrolled = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mScrolled) {
                    mScrolled = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx != 0 || dy != 0) {
                    mScrolled = true;
                }
            }
        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

//        LinearEndSnapHelper snapHelper = new LinearEndSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

    }


    public int findLastItemPosition() {
        return linearLayoutManager.findLastCompletelyVisibleItemPosition();
    }

    public boolean canScrollPullLeft() {
        int lastChild = findLastItemPosition();
        if (lastChild + 1 >= recyclerView.getAdapter().getItemCount()) {
            return true;
        }
        return false;
    }

    public static int getResId(String variableName, @NonNull Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
