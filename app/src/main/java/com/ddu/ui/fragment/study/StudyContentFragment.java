package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ddu.app.App;
import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.fragment.AbstractRecycleViewFragment;
import com.ddu.icore.util.MultiHashMap;
import com.ddu.ui.adapter.StudyRecycleViewAdapter;
import com.ddu.ui.fragment.WebFragment;
import com.ddu.ui.fragment.person.PersonalInfoFragment;
import com.ddu.ui.fragment.study.imitate.UIShapeFragment;
import com.ddu.ui.fragment.study.ui.BottomSheetFragment;
import com.ddu.ui.fragment.study.ui.CameraFragment;
import com.ddu.ui.fragment.study.ui.ConstraintLayoutFragment;
import com.ddu.ui.fragment.study.ui.DRVFragment;
import com.ddu.ui.fragment.study.ui.DesignFragment;
import com.ddu.ui.fragment.study.ui.DrawViewFragment;
import com.ddu.ui.fragment.study.ui.FlexboxFragment;
import com.ddu.ui.fragment.study.ui.FrameLayoutFragment;
import com.ddu.ui.fragment.study.ui.ImageFragment;
import com.ddu.ui.fragment.study.ui.InnerScrollViewFragment;
import com.ddu.ui.fragment.study.ui.KotlinFragment;
import com.ddu.ui.fragment.study.ui.PaletteFragment;
import com.ddu.ui.fragment.study.ui.PathMeasureFragment;
import com.ddu.ui.fragment.study.ui.ProgressWheelFragment;
import com.ddu.ui.fragment.study.ui.RecyclerViewFragment;
import com.ddu.ui.fragment.study.ui.RenderScriptFragment;
import com.ddu.ui.fragment.study.ui.ScrollViewFragment;
import com.ddu.ui.fragment.study.ui.SegmentPullToRefreshFragment;
import com.ddu.ui.fragment.study.ui.ShapeAdvancedFragment;
import com.ddu.ui.fragment.study.ui.ShapeFragment;
import com.ddu.ui.fragment.study.ui.ShapeInjectFragment;
import com.ddu.ui.fragment.study.ui.ShowDialogFragment;
import com.ddu.ui.fragment.study.ui.SnackBarFragment;
import com.ddu.ui.fragment.study.ui.SwipeRefreshFragment;
import com.ddu.ui.fragment.study.ui.TabPageIndicatorFragment;
import com.ddu.ui.fragment.study.ui.TextViewFragment;
import com.ddu.ui.fragment.study.ui.ToolBarFragment;
import com.ddu.ui.fragment.study.ui.UITestFragment;
import com.ddu.ui.fragment.study.ui.ViewFragment;
import com.ddu.ui.fragment.study.ui.WifiFragment;
import com.ddu.ui.fragment.study.ui.WuBaFragment;
import com.iannotation.Tuple;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yzbzz on 2017/5/9.
 */

public class StudyContentFragment extends AbstractRecycleViewFragment<ItemEntity, StudyRecycleViewAdapter> implements StudyRecycleViewAdapter.ItemClickListener {

    private static final String TAG = "ARGUMENT_TASK_ID";
    private static final String TAG_S = "ARGUMENT_TASK_ID_S";


    private static MultiHashMap<Integer, Class<?>> mMaps = new MultiHashMap<>();

    static {
        mMaps.put(0, WifiFragment.class);
        mMaps.put(0, ProgressWheelFragment.class);
        mMaps.put(0, SnackBarFragment.class);
        mMaps.put(0, ShapeAdvancedFragment.class);
        mMaps.put(0, UIShapeFragment.class);
        mMaps.put(0, ConstraintLayoutFragment.class);
        mMaps.put(0, CameraFragment.class);
        mMaps.put(0, PaletteFragment.class);
        mMaps.put(0, ViewFragment.class);
        mMaps.put(0, WebFragment.class);
        mMaps.put(0, WuBaFragment.class);
        mMaps.put(0, PathMeasureFragment.class);
        mMaps.put(0, FrameLayoutFragment.class);
        mMaps.put(0, TabPageIndicatorFragment.class);
        mMaps.put(0, ScrollViewFragment.class);
        mMaps.put(0, PersonalInfoFragment.class);
        mMaps.put(0, SegmentPullToRefreshFragment.class);
        mMaps.put(0, InnerScrollViewFragment.class);
        mMaps.put(0, ShowDialogFragment.class);
        mMaps.put(0, DesignFragment.class);
        mMaps.put(0, ShapeInjectFragment.class);
        mMaps.put(0, DrawViewFragment.class);
        mMaps.put(0, DRVFragment.class);
        mMaps.put(0, FlexboxFragment.class);
        mMaps.put(0, ImageFragment.class);
        mMaps.put(0, RecyclerViewFragment.class);
        mMaps.put(0, RenderScriptFragment.class);
        mMaps.put(0, ShapeFragment.class);
        mMaps.put(0, SwipeRefreshFragment.class);
        mMaps.put(0, TextViewFragment.class);
        mMaps.put(0, ToolBarFragment.class);
        mMaps.put(0, BottomSheetFragment.class);
        mMaps.put(0, KotlinFragment.class);
        mMaps.put(0, UITestFragment.class);
    }

    private int index;
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
            index = getArguments().getInt(TAG);
            tag = getArguments().getString(TAG_S, "");
        }

        ArrayList<Tuple<String, Class<?>>> keys;
//        if (TextUtils.isEmpty(tag)) {
//            keys = mMaps.get(index);
//        } else {
        keys = App.Companion.getMp().provide(tag);
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
