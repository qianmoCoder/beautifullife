package com.ddu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;

/**
 * Created by yzbzz on 16/4/6.
 */
public class StudyFragment extends DefaultFragment {

//    @Nullable
//    @BindView(R.id.rv_study)
//    RecyclerView mRvStudy;
//
//    private RecyclerView.LayoutManager mLayoutManager;
//    @Nullable
//    private StudyRecycleViewAdapter mStudyRecycleViewAdapter;
//
//    private List<StudyContent> mItems;
//
//    private ItemTouchHelper mItemTouchHelper;
//
//    private Unbinder unbinder;
//
    @NonNull
    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        mItems = new ArrayList<>();
//        StudyContent content = new StudyContent();
//        mItems.add(content);
//        List<StudyContent> studyContents = DbManager.getStudyContentDao().loadAll();
//        Subscription subscription = Observable.from(studyContents)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<StudyContent>() {
//                    @Override
//                    public void call(StudyContent studyContent) {
//                        mItems.add(studyContent);
//                    }
//                });
//        addSubscription(subscription);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    public void initView() {
//        unbinder = ButterKnife.bind(this, mView);
//
//        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        mRvStudy.setLayoutManager(mLayoutManager);
//        mStudyRecycleViewAdapter = new StudyRecycleViewAdapter(mContext, mItems, "学习", null);
//        mRvStudy.setAdapter(mStudyRecycleViewAdapter);

//        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(mStudyRecycleViewAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(mRvStudy);
//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(mRvStudy);
        setTitle("学习");
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
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }
}
