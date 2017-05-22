package com.ddu.ui.fragment.study.imitate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.ddu.icore.ui.adapter.common.DefaultListViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.FragmentUtils;
import com.ddu.R;
import com.ddu.ui.fragment.study.ui.ShapeFragment;
import com.ddu.ui.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/8.
 */
public class UIFragment extends DefaultFragment implements AdapterView.OnItemClickListener {


    @Nullable
    @BindView(R.id.lv_ui_navigation)
    ListViewForScrollView lvUiNavigation;
    @Nullable
    @BindView(R.id.ls_ui_navigation)
    ScrollView lsUiNavigation;
    @Nullable
    @BindView(R.id.fl_ui_detail)
    FrameLayout flUiDetail;

    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> mList;

    private Unbinder unbinder;

    @Override
    public void initData(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add(i + "");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);

        lvUiNavigation.setAdapter(new DefaultListViewAdapter<String>(mContext,mList) {
            @Override
            public void bindView(int position, String s, @NonNull ViewHolder holder) {
                holder.setText(R.id.tv_navi_name, s);
            }

            @Override
            public int getLayoutId() {
                return R.layout.fragment_ui_navi_item;
            }
        });

        lvUiNavigation.setOnItemClickListener(this);
        setDefaultTitle("UI");
    }

    public void initTitle() {
        setDefaultTitle("UI");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, @NonNull View view, int position, long id) {
        lsUiNavigation.smoothScrollTo(0, position * view.getHeight());
        ShapeFragment shapeFragment = ShapeFragment.newInstance("");
        FragmentUtils.replaceFragment(baseActivity.getSupportFragmentManager(), shapeFragment, R.id.fl_ui_detail);
    }
}
