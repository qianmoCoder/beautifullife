package com.ddu.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.dialog.DefaultGridBottomAdapter;
import com.ddu.icore.entity.BottomItemEntity;
import com.ddu.util.ToastUtils;
import com.ddu.icore.util.sys.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzbzz on 2017/5/26.
 */

public class BottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView mTvCancel;
    private RecyclerView mRecyclerView;

    private List<BottomItemEntity> shareEntities;
    private DefaultGridBottomAdapter shareAdapter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareEntities = new ArrayList<>();

        BottomItemEntity shareEntity = new BottomItemEntity();
        shareEntity.setName("QQ");
        shareEntity.setResId(R.drawable.weixin_friend_share);

        BottomItemEntity shareEntity1 = new BottomItemEntity();
        shareEntity1.setName("weixin");
        shareEntity1.setResId(R.drawable.weixin_circle_friend_share);

        BottomItemEntity shareEntity2 = new BottomItemEntity();
        shareEntity2.setName("feixin");
        shareEntity2.setResId(R.drawable.weixin_friend_share);

        BottomItemEntity shareEntity3 = new BottomItemEntity();
        shareEntity3.setName("yixin");
        shareEntity3.setResId(R.drawable.weixin_circle_friend_share);

        BottomItemEntity shareEntity4 = new BottomItemEntity();
        shareEntity4.setName("weibo");
        shareEntity4.setResId(R.drawable.weixin_friend_share);

        shareEntities.add(shareEntity);
        shareEntities.add(shareEntity1);
        shareEntities.add(shareEntity2);
        shareEntities.add(shareEntity3);
        shareEntities.add(shareEntity4);
        shareEntities.add(shareEntity4);
        shareEntities.add(shareEntity4);
        shareEntities.add(shareEntity4);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static BottomSheetDialog newInstance() {
        BottomSheetDialog dialog = new BottomSheetDialog();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_bottom_view, container, false);

        mTvCancel = ViewUtils.findViewById(linearLayout, R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);

        mRecyclerView = ViewUtils.findViewById(linearLayout, R.id.rv_items);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        shareAdapter = new DefaultGridBottomAdapter(getContext(), shareEntities);
        mRecyclerView.setAdapter(shareAdapter);

        shareAdapter.setOnItemClickListener(new DefaultGridBottomAdapter.OnClickListener<BottomItemEntity>() {
            @Override
            public void onClick(BottomItemEntity data, int position) {
                ToastUtils.showToast(data.getName() + " " + position);
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
