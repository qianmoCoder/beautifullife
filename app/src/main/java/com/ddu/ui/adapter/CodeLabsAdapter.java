package com.ddu.ui.adapter;

import android.content.Context;

import com.ddu.R;
import com.ddu.icore.callback.InConsumer1;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;
import com.ddu.icore.ui.view.OptionItemView;
import com.ddu.model.WorkEntity;
import com.ddu.model.WorkEntityKt;

import java.util.List;

public class CodeLabsAdapter extends DefaultRVAdapter<WorkEntity> {

    private InConsumer1<WorkEntity> itemClickListener;

    public CodeLabsAdapter(Context context, List<WorkEntity> items) {
        super(context, items);
    }

    @Override
    public int getLayoutId(int viewType) {
        int layoutId;
        if (viewType == WorkEntityKt.TYPE_TITLE) {
            layoutId = R.layout.work_kotlin_code_labs_rv_title;
        } else {
            layoutId = R.layout.work_kotlin_code_labs_rv_content;
        }
        return layoutId;
    }

    @Override
    public int getDefItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public void bindView(ViewHolder viewHolder, WorkEntity data, int position) {
        if (getDefItemViewType(position) == WorkEntityKt.TYPE_TITLE) {
            viewHolder.setText(R.id.tv_title, data.getTitle());
        } else {
            OptionItemView optionItemView = viewHolder.getView(R.id.oiv_content);
            optionItemView.setLeftText(data.getNumber());
            optionItemView.setTitle(data.getContent());
            viewHolder.itemView.setOnClickListener(v -> {
                if (null != itemClickListener) {
                    itemClickListener.accept(data);
                }
            });
        }
    }

    public void setItemClickListener(InConsumer1<WorkEntity> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

