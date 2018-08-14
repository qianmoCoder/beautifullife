package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.text.TextUtils;

import com.ddu.app.App;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.fragment.AbsRVFragment;
import com.ddu.ui.adapter.UIRecycleViewAdapter;
import com.iannotation.Tuple;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yzbzz on 2017/5/16.
 */
public abstract class ContentFragment extends AbsRVFragment<ItemEntity, UIRecycleViewAdapter> implements UIRecycleViewAdapter.ItemClickListener {

    @Override
    public void initData(Bundle savedInstanceState) {

        ArrayList<Tuple<String, Class<?>>> keys = App.Companion.getElementProvider().provide(getUrl());
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
    public UIRecycleViewAdapter getAdapter() {
        return new UIRecycleViewAdapter(getMContext(), mDataEntities);
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

    public abstract String getUrl();

}
