package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ddu.R;
import com.ddu.db.DbManager;
import com.ddu.db.entity.StudyContent;
import com.ddu.icore.ui.adapter.common.DefaultRecycleViewAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/16.
 */

public class StudyTagsFragment extends DefaultFragment {

    private static final int SPAN_COUNT = 4;

    private List<StudyContent> studyContents = new ArrayList<>();
    private List<StudyContent> studyContentOld = new ArrayList<>();
    private List<StudyContent> studyContentNew = new ArrayList<>();

    private GridLayoutManager mOldGridLayoutManager;
    private GridLayoutManager mNewGridLayoutManager;
    private RecyclerView mRvOld;
    private RecyclerView mRvNew;

    public static StudyTagsFragment newInstance() {
        Bundle args = new Bundle();
        StudyTagsFragment fragment = new StudyTagsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        studyContents = DbManager.getStudyContentDao().loadAll();

        for (StudyContent studyContent : studyContents) {
            if (studyContent.getIsOld()) {
                studyContentOld.add(studyContent);
            } else {
                studyContentNew.add(studyContent);
            }
        }

        mOldGridLayoutManager = new GridLayoutManager(mContext, SPAN_COUNT);
        mNewGridLayoutManager = new GridLayoutManager(mContext, SPAN_COUNT);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_tags;
    }

    @Override
    public void initView() {
        mRvOld = findViewById(R.id.rv_old);
        mRvNew = findViewById(R.id.rv_new);

        mRvOld.setLayoutManager(mOldGridLayoutManager);
        mRvNew.setLayoutManager(mNewGridLayoutManager);

        mRvOld.setAdapter(new DefaultRecycleViewAdapter<StudyContent>(mContext, studyContentOld) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_tag_view;
            }

            @Override
            public void bindView(ViewHolder viewHolder, StudyContent data, int position) {
                viewHolder.setText(R.id.tv_tag, data.getTitle());
            }
        });

        mRvNew.setAdapter(new DefaultRecycleViewAdapter<StudyContent>(mContext, studyContentNew) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_tag_view;
            }

            @Override
            public void bindView(ViewHolder viewHolder, StudyContent data, int position) {
                viewHolder.setText(R.id.tv_tag, data.getTitle());
            }
        });

    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
