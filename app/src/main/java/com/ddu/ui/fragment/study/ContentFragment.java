package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.ddu.app.App;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.ui.fragment.AbsRVFragment;
import com.ddu.ui.adapter.ContentRVAdapter;
import com.iannotation.Tuple;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yzbzz on 2017/5/16.
 */
public abstract class ContentFragment extends AbsRVFragment<ItemEntity, ContentRVAdapter> implements ContentRVAdapter.ItemClickListener {

    @Override
    public void initData(Bundle savedInstanceState) {

        String color = getArguments().getString("bgColor", "");
        String url = getArguments().getString("url", "");
        if (TextUtils.isEmpty(url)) {
            url = getUrl();
        }

        ArrayList<Tuple<String, Class<?>>> keys = App.Companion.getElementProvider().provide(url);
        for (Tuple<String, Class<?>> key : keys) {
            ItemEntity itemEntity = new ItemEntity();

            String first = key.first;
            Class<?> second = key.second;

            String title = TextUtils.isEmpty(first) ? second.getSimpleName() : first;
            itemEntity.setTitle(title);
            itemEntity.setColor(color);
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
    public ContentRVAdapter getAdapter() {
        return new ContentRVAdapter(getMContext(), mDataEntities);
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
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }

    public String getUrl() {
        return "";
    }

}
