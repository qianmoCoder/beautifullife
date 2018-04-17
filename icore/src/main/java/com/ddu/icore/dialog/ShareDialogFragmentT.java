//
//package com.ddu.icore.dialog;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ddu.icore.R;
//import com.ddu.icore.entity.BottomItemEntity;
//import com.ddu.icore.util.ToastUtils;
//import com.ddu.icore.util.sys.ViewUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ShareDialogFragmentT extends AbsBottomDialogFragment implements View.OnClickListener {
//
//    private static int SPAN_COUNT = 3;
//
//    private TextView mTvCancel;
//    private RecyclerView mRecyclerView;
//
//    private List<BottomItemEntity> shareEntities;
//    private DefaultGridBottomAdapter shareAdapter;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        shareEntities = new ArrayList<>();
//
//        BottomItemEntity shareEntity = new BottomItemEntity();
//        shareEntity.setName("QQ");
//        shareEntity.setResId(R.drawable.weixin_friend_share);
//
//        BottomItemEntity shareEntity1 = new BottomItemEntity();
//        shareEntity1.setName("weixin");
//        shareEntity1.setResId(R.drawable.weixin_circle_friend_share);
//
//        BottomItemEntity shareEntity2 = new BottomItemEntity();
//        shareEntity2.setName("feixin");
//        shareEntity2.setResId(R.drawable.weixin_friend_share);
//
//        BottomItemEntity shareEntity3 = new BottomItemEntity();
//        shareEntity3.setName("yixin");
//        shareEntity3.setResId(R.drawable.weixin_circle_friend_share);
//
//        BottomItemEntity shareEntity4 = new BottomItemEntity();
//        shareEntity4.setName("weibo");
//        shareEntity4.setResId(R.drawable.weixin_friend_share);
//
//        shareEntities.add(shareEntity);
//        shareEntities.add(shareEntity1);
//        shareEntities.add(shareEntity2);
//        shareEntities.add(shareEntity3);
////        shareEntities.add(shareEntity4);
//
//    }
//
//    public static ShareDialogFragmentT newInstance() {
//        ShareDialogFragmentT dialog = new ShareDialogFragmentT();
//        return dialog;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.dialog_fragment_bottom_content, container, false);
//
//        mTvCancel = ViewUtils.findViewById(linearLayout, R.id.tv_cancel);
//        mTvCancel.setOnClickListener(this);
//
//        mRecyclerView = ViewUtils.findViewById(linearLayout, R.id.rv_share);
//
//        int spanCount = shareEntities.size() < SPAN_COUNT ? shareEntities.size() % SPAN_COUNT : SPAN_COUNT;
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//
//        shareAdapter = new DefaultGridBottomAdapter(getContext(), shareEntities);
//        mRecyclerView.setAdapter(shareAdapter);
//
//        shareAdapter.setOnItemClickListener(new DefaultGridBottomAdapter.OnClickListener<BottomItemEntity>() {
//            @Override
//            public void onClick(BottomItemEntity data, int position) {
//                ToastUtils.showToast(data.getName() + " " + position);
//            }
//        });
//
//
//        return linearLayout;
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.tv_cancel) {
//            dismissAllowingStateLoss();
//        }
//    }
//}
