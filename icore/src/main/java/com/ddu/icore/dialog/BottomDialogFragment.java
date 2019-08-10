package com.ddu.icore.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddu.icore.R;
import com.ddu.icore.callback.Consumer2;
import com.ddu.icore.callback.Consumer3;
import com.ddu.icore.entity.BottomItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yzbzz on 2018/8/8.
 */
public class BottomDialogFragment extends BottomSheetDialogFragment {

    private BottomDialogParams mParams;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.i_dialog_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(mParams.mTitle);

        RecyclerView rvItems = view.findViewById(R.id.rv_items);

        if (null != mParams) {
            RecyclerView.LayoutManager layoutManager;
            if (mParams.mLayoutType == BottomDialogParams.TYPE_GRID) {
                int size = mParams.mItems.size();
                int spanCount = mParams.mSpanCount;
                int count = size < spanCount ? size % spanCount : spanCount;
                layoutManager = new GridLayoutManager(mContext, count);
            } else {
                layoutManager = new LinearLayoutManager(mContext);
            }
            rvItems.setLayoutManager(layoutManager);
            BottomAdapter adapter = new BottomAdapter(getContext(), mParams);
            adapter.setConsumer2(new Consumer2<BottomItem, Integer>() {
                @Override
                public void accept(BottomItem item, Integer integer) {
                    mParams.mConsumer3.accept(BottomDialogFragment.this, item, integer);
                }
            });
            rvItems.setAdapter(adapter);
        }

    }

    public static class Builder {

        private BottomDialogParams p;

        public Builder() {
            p = new BottomDialogParams();
        }

        public Builder setTitle(CharSequence title) {
            p.mTitle = title;
            return this;
        }

        public Builder linearLayout() {
            p.mLayoutType = BottomDialogParams.TYPE_LINEAR;
            return this;
        }

        public Builder gridLayout() {
            gridLayout(4);
            return this;
        }

        public Builder gridLayout(int count) {
            p.mLayoutType = BottomDialogParams.TYPE_GRID;
            p.mSpanCount = count;
            return this;
        }

        public Builder setItems(List<BottomItem> items) {
            p.mItems = items;
            return this;
        }

        public Builder addItem(BottomItem item) {
            if (null == p.mItems) {
                p.mItems = new ArrayList<>();
            }
            p.mItems.add(item);
            return this;
        }

        public Builder setItemClickListener(Consumer3<DialogFragment, BottomItem, Integer> callBack) {
            p.mConsumer3 = callBack;
            return this;
        }

        public Builder setIconSize(int width, int height) {
            p.mIconWidth = width;
            p.mIconHeight = height;
            return this;
        }

        public Builder setBottomText(CharSequence bottomText) {
            p.mBottomText = bottomText;
            return this;
        }

        public Builder setScale(int scale) {
            p.mScale = scale;
            return this;
        }

        public BottomDialogFragment create() {
            BottomDialogFragment dialogFragment = new BottomDialogFragment();
            dialogFragment.setParams(p);
            return dialogFragment;
        }
    }

    public void setParams(BottomDialogParams params) {
        this.mParams = params;
    }

    public BottomDialogParams getParams() {
        return mParams;
    }

    public void showDialog(FragmentActivity activity) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        showNow(activity.getSupportFragmentManager(), "BottomDialogFragment");
    }

    public void dismissDialog() {
        dismissAllowingStateLoss();
    }

}
