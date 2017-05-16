
package com.ddu.icore.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.entity.ShareEntity;
import com.ddu.icore.util.ToastUtils;
import com.ddu.icore.util.sys.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class ShareDialogFragment extends BottomDialogFragment implements View.OnClickListener {

    private static int SPAN_COUNT = 3;

    private TextView mTvCancel;
    private RecyclerView mRecyclerView;

    private List<ShareEntity> shareEntities;
    private ShareAdapter shareAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareEntities = new ArrayList<>();

        ShareEntity shareEntity = new ShareEntity();
        shareEntity.setName("QQ");
        shareEntity.setResId(R.drawable.weixin_friend_share);

        ShareEntity shareEntity1 = new ShareEntity();
        shareEntity1.setName("weixin");
        shareEntity1.setResId(R.drawable.weixin_circle_friend_share);

        ShareEntity shareEntity2 = new ShareEntity();
        shareEntity2.setName("feixin");
        shareEntity2.setResId(R.drawable.weixin_friend_share);

        ShareEntity shareEntity3 = new ShareEntity();
        shareEntity3.setName("yixin");
        shareEntity3.setResId(R.drawable.weixin_circle_friend_share);

        ShareEntity shareEntity4 = new ShareEntity();
        shareEntity4.setName("weibo");
        shareEntity4.setResId(R.drawable.weixin_friend_share);

        shareEntities.add(shareEntity);
        shareEntities.add(shareEntity1);
        shareEntities.add(shareEntity2);
        shareEntities.add(shareEntity3);
//        shareEntities.add(shareEntity4);

    }

    public static ShareDialogFragment newInstance() {
        ShareDialogFragment dialog = new ShareDialogFragment();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_share, container, false);

        mTvCancel = ViewUtils.findViewById(linearLayout, R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        mRecyclerView = ViewUtils.findViewById(linearLayout, R.id.rv_share);

        int spanCount = shareEntities.size() < SPAN_COUNT ? shareEntities.size() % SPAN_COUNT : SPAN_COUNT;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        shareAdapter = new ShareAdapter(getContext(), shareEntities);
        mRecyclerView.setAdapter(shareAdapter);

        shareAdapter.setOnItemClickListener(new ShareAdapter.OnClickListener<ShareEntity>() {
            @Override
            public void onClick(ShareEntity data, int position) {
                ToastUtils.showTextToast(data.getName() + " " + position);
            }
        });


        return linearLayout;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            dismissAllowingStateLoss();
        }
    }
}
