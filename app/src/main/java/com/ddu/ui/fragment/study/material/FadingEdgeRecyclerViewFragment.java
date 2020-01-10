package com.ddu.ui.fragment.study.material;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

import java.util.ArrayList;

/**
 * Created by yzbzz on 2017/3/31.
 */
@IElement("MD")
public class FadingEdgeRecyclerViewFragment extends DefaultFragment {

    private RecyclerView recyclerView;

    private ArrayList<Integer> items = new ArrayList<>();


    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fading_edge_recycle_view;
    }

    private LinearLayoutManager linearLayoutManager;

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.rv_up);


        linearLayoutManager = new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < 100; i++) {
            items.add(i);
        }

        recyclerView.setAdapter(new DefaultRVAdapter<Integer>(mContext, items) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.recyclerview_item_default;
            }

            @Override
            public void bindView(ViewHolder viewHolder, Integer data, int position) {
                viewHolder.setText(R.id.tv_detail, data + "");
            }
        });

        recyclerView.setHasFixedSize(true);
    }

}
