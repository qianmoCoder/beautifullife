package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.text.TextUtils;

import com.ddu.app.App;
import com.ddu.icore.callback.Consumer1;
import com.ddu.icore.ui.fragment.AbsRVFragment;
import com.ddu.ui.adapter.ContentRVAdapter;
import com.iannotation.Tuple;
import com.iannotation.model.RouteMeta;

import java.util.ArrayList;
import java.util.Collections;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yzbzz on 2017/5/16.
 */
public abstract class ContentFragment extends AbsRVFragment<RouteMeta, ContentRVAdapter> implements Consumer1<RouteMeta> {

    @Override
    public void initData(Bundle savedInstanceState) {

        String color = getArguments().getString("bgColor", "");
        String url = getArguments().getString("url", "");
        if (TextUtils.isEmpty(url)) {
            url = getUrl();
        }

        ArrayList<Tuple<String, Class<?>>> keys = App.Companion.getElementProvider().provide(url);
        for (Tuple<String, Class<?>> key : keys) {
            String first = key.first;
            Class<?> second = key.second;

            String title = TextUtils.isEmpty(first) ? second.getSimpleName() : first;

            RouteMeta routeMeta = RouteMeta.build("", title, color, "", second);
            mDataEntities.add(routeMeta);
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
    public void accept(RouteMeta data) {
        Bundle bundle = new Bundle();
        bundle.putString("title", data.getText());
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
