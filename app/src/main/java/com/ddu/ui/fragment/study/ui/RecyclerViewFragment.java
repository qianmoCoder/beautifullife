package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;

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

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.rv_up);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));

        recyclerView.setAdapter(new DefaultRecycleViewAdapter<Integer>(mContext, localImages) {

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_study_recycle_list_item;
            }

            @Override
            public void bindView(ViewHolder viewHolder, Integer data, int position) {
                viewHolder.setImageResource(R.id.iv_up, data);
            }
        });
        recyclerView.setHasFixedSize(true);

//        PagerStartSnapHelper snapHelper = new PagerStartSnapHelper();
//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

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
