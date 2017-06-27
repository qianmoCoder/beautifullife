package com.ddu.ui.fragment.study;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ddu.db.entity.ItemEntity;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.ui.fragment.AbstractRecycleViewFragment;
import com.ddu.icore.util.MultiHashMap;
import com.ddu.ui.adapter.StudyRecycleViewAdapter;
import com.ddu.ui.fragment.person.PersonalInfoFragment;
import com.ddu.ui.fragment.study.ui.BottomSheetFragment;
import com.ddu.ui.fragment.study.ui.DRVFragment;
import com.ddu.ui.fragment.study.ui.DesignFragment;
import com.ddu.ui.fragment.study.ui.DialogFragment;
import com.ddu.ui.fragment.study.ui.DrawFragment;
import com.ddu.ui.fragment.study.ui.FlexboxFragment;
import com.ddu.ui.fragment.study.ui.ImageFragment;
import com.ddu.ui.fragment.study.ui.InnerScrollViewFragment;
import com.ddu.ui.fragment.study.ui.RecyclerViewFragment;
import com.ddu.ui.fragment.study.ui.RenderScriptFragment;
import com.ddu.ui.fragment.study.ui.ScrollViewFragment;
import com.ddu.ui.fragment.study.ui.SegmentPullToRefreshFragment;
import com.ddu.ui.fragment.study.ui.ShapeFragment;
import com.ddu.ui.fragment.study.ui.SwipeRefreshFragment;
import com.ddu.ui.fragment.study.ui.TabPageIndicatorFragment;
import com.ddu.ui.fragment.study.ui.TextViewFragment;
import com.ddu.ui.fragment.study.ui.ToolBarFragment;

import java.util.ArrayList;

/**
 * Created by yzbzz on 2017/5/9.
 */

public class StudyContentFragment extends AbstractRecycleViewFragment<ItemEntity, StudyRecycleViewAdapter> implements StudyRecycleViewAdapter.ItemClickListener {

    private static final String TAG = "ARGUMENT_TASK_ID";


    private static MultiHashMap<Integer, Class> mMaps = new MultiHashMap<>();

    static {
        mMaps.put(0, TabPageIndicatorFragment.class);
        mMaps.put(0, ScrollViewFragment.class);
        mMaps.put(0, PersonalInfoFragment.class);
        mMaps.put(0, SegmentPullToRefreshFragment.class);
        mMaps.put(0, InnerScrollViewFragment.class);
        mMaps.put(0, DialogFragment.class);
        mMaps.put(0, DesignFragment.class);
        mMaps.put(0, DrawFragment.class);
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
    }

    private int index;

    @NonNull
    public static StudyContentFragment newInstance(int index) {
        StudyContentFragment fragment = new StudyContentFragment();
        Bundle args = new Bundle();
        args.putInt(TAG, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (null != getArguments()) {
            index = getArguments().getInt(TAG);
        }
        ArrayList<Class> keys = mMaps.get(index);
        for (Class key : keys) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setTitle(key.getSimpleName());
            itemEntity.setClassName(key.getName());
            mDataEntities.add(itemEntity);
        }
    }


    @Override
    public void initView() {
        super.initView();
        mAdapter.setItemClickListener(this);
    }


    @Override
    public StudyRecycleViewAdapter getAdapter() {
        return new StudyRecycleViewAdapter(mContext, mDataEntities);
    }

    @Override
    public void initRefreshView() {
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    //    @NonNull
//    private ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
//        @Override
//        public int getMovementFlags(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
////            Vibrator vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
////            vibrator.vibrate(70);
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            int dragFlags;
//            int swipeFlags;
//            if (layoutManager instanceof GridLayoutManager) {
//                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//            } else {
//                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//            }
//            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        }
//
//        @Override
//        public boolean onMove(RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            int fromPosition = viewHolder.getAdapterPosition();
//            int toPosition = target.getAdapterPosition();
//            if (fromPosition < toPosition) {
//                for (int i = fromPosition; i < toPosition; i++) {
//                    Collections.swap(mItems, i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    Collections.swap(mItems, i, i - 1);
//                }
//            }
//            mStudyRecycleViewAdapter.notifyItemMoved(fromPosition, toPosition);
//            return false;
//        }
//
//        @Override
//        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//
//        }
//
//        @Override
//        public void onSelectedChanged(@NonNull RecyclerView.ViewHolder viewHolder, int actionState) {
//            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                CardView cardView = (CardView) viewHolder.itemView;
//                cardView.setCardBackgroundColor(Color.LTGRAY);
//            }
//            super.onSelectedChanged(viewHolder, actionState);
//        }
//
//        @Override
//        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//            super.clearView(recyclerView, viewHolder);
//            CardView cardView = (CardView) viewHolder.itemView;
//            cardView.setCardBackgroundColor(Color.WHITE);
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(ItemEntity data) {
        startFragment(data.getClassName());
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
