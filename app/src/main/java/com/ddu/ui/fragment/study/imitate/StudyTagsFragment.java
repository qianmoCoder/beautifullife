package com.ddu.ui.fragment.study.imitate;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ddu.R;
import com.ddu.db.DbManager;
import com.ddu.db.entity.StudyContent;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.view.ShapeTextView;
import com.iannotation.IElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/16.
 */

@IElement("HI")
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
        studyContents = DbManager.getStudyContentDao().getStudyContents();

        for (StudyContent studyContent : studyContents) {
            if (studyContent.isOld()) {
                studyContentOld.add(studyContent);
            } else {
                studyContentNew.add(studyContent);
            }
        }

        mOldGridLayoutManager = new GridLayoutManager(getMContext(), SPAN_COUNT);
        mNewGridLayoutManager = new GridLayoutManager(getMContext(), SPAN_COUNT);
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

        mRvOld.setAdapter(new DefaultRVAdapter<StudyContent>(getMContext(), studyContentOld) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_tag_view;
            }

            @Override
            public void bindView(ViewHolder viewHolder, StudyContent data, int position) {
                viewHolder.setText(R.id.tv_tag, data.getTitle());
            }
        });

        mRvNew.setAdapter(new DefaultRVAdapter<StudyContent>(getMContext(), studyContentNew) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_tag_view;
            }

            @Override
            public void bindView(ViewHolder viewHolder, StudyContent data, int position) {
                viewHolder.setText(R.id.tv_tag, data.getTitle());
            }
        });

        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        //将ItemTouchHelper和RecyclerView建立关联
        helper.attachToRecyclerView(mRvOld);

    }

    //    @NonNull
    private ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            Vibrator vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
//            vibrator.vibrate(70);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int dragFlags;
            int swipeFlags;
            if (layoutManager instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(studyContentOld, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(studyContentOld, i, i - 1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(@NonNull RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                ShapeTextView cardView = (ShapeTextView) viewHolder.itemView;
                cardView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            ShapeTextView cardView = (ShapeTextView) viewHolder.itemView;
            cardView.setBackgroundColor(Color.WHITE);
        }
    };

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
