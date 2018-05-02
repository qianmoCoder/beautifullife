package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.adapter.StudyUIRecycleViewAdapter;
import com.iannotation.ContentType;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by yzbzz on 2017/3/31.
 */
@ContentType("UI")
public class RecyclerViewFragment extends DefaultFragment {

    private RecyclerView recyclerView;
    private StudyUIRecycleViewAdapter studyUIRecycleViewAdapter;
    private Button btnSingle;
    private Button btnMulti;

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
        btnSingle = findViewById(R.id.btn_single);
        btnMulti = findViewById(R.id.btn_multi);

        linearLayoutManager = new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int position = 0; position < 7; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }

        recyclerView.setAdapter(studyUIRecycleViewAdapter = new StudyUIRecycleViewAdapter(getMContext(), localImages));

        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
//        LinearEndSnapHelper snapHelper = new LinearEndSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//        LinearEndSnapHelper snapHelper = new LinearEndSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyUIRecycleViewAdapter.setChoiceMode(StudyUIRecycleViewAdapter.CHOICE_MODE_SINGLE);
            }
        });
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyUIRecycleViewAdapter.setChoiceMode(StudyUIRecycleViewAdapter.CHOICE_MODE_MULTIPLE);
            }
        });
    }


    public int findLastItemPosition() {
        return linearLayoutManager.findLastCompletelyVisibleItemPosition();
    }

    public boolean canScrollPullLeft() {
        int lastChild = findLastItemPosition();
        return lastChild + 1 >= recyclerView.getAdapter().getItemCount();
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
