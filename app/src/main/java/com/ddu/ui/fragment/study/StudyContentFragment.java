package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ddu.app.App;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.fragment.AbstractRecycleViewFragment;
import com.ddu.ui.adapter.StudyRecycleViewAdapter;
import com.iannotation.Tuple;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yzbzz on 2017/5/9.
 */

public class StudyContentFragment extends AbstractRecycleViewFragment<ItemEntity, StudyRecycleViewAdapter> implements StudyRecycleViewAdapter.ItemClickListener {

    private static final String TAG = "ARGUMENT_TASK_ID";
    private static final String TAG_S = "ARGUMENT_TASK_ID_S";

    private String tag;

    @NonNull
    public static StudyContentFragment newInstance(int index) {
        StudyContentFragment fragment = new StudyContentFragment();
        Bundle args = new Bundle();
        args.putInt(TAG, index);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public static StudyContentFragment newInstance(String tag) {
        StudyContentFragment fragment = new StudyContentFragment();
        Bundle args = new Bundle();
        args.putString(TAG_S, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (null != getArguments()) {
//            index = getArguments().getInt(TAG);
            tag = getArguments().getString(TAG_S, "");
        }

        ArrayList<Tuple<String, Class<?>>> keys;
//        if (TextUtils.isEmpty(tag)) {
//            keys = mMaps.get(index);
//        } else {
        keys = App.Companion.getElementProvider().provide(tag);
//        }
        for (Tuple<String, Class<?>> key : keys) {
            ItemEntity itemEntity = new ItemEntity();

            String first = key.first;
            Class<?> second = key.second;

            String title = TextUtils.isEmpty(first) ? second.getSimpleName() : first;
            itemEntity.setTitle(title);
            itemEntity.setClassName(second.getName());
            mDataEntities.add(itemEntity);
        }
        Collections.sort(mDataEntities);
    }


    @Override
    public void initView() {
        super.initView();
        mAdapter.setItemClickListener(this);
    }


    @Override
    public StudyRecycleViewAdapter getAdapter() {
        return new StudyRecycleViewAdapter(getMContext(), mDataEntities);
    }

    @Override
    public void initRefreshView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(ItemEntity data) {
        Bundle bundle = new Bundle();
        bundle.putString("title", data.getTitle());
        startFragment(data.getClassName(), bundle);
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
