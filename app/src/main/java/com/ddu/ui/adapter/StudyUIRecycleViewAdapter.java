package com.ddu.ui.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import com.ddu.R;
import com.ddu.icore.ui.adapter.common.DefaultRVAdapter;
import com.ddu.icore.ui.adapter.common.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by yzbzz on 2017/12/5.
 */

public class StudyUIRecycleViewAdapter extends DefaultRVAdapter<Integer> {

    public static final int CHOICE_MODE_SINGLE = 1;
    public static final int CHOICE_MODE_MULTIPLE = 2;

    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    private int choiceMode = CHOICE_MODE_SINGLE;

    public StudyUIRecycleViewAdapter(Context context, List<Integer> items) {
        super(context, items);
    }

    public void clearChoices() {
        if (mCheckStates != null) {
            mCheckStates.clear();
        }
    }

    public int getChoiceMode() {
        return choiceMode;
    }

    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.fragment_study_recycle_list_item;
    }

    @Override
    public void bindView(ViewHolder viewHolder, Integer data, int position) {
//        DisplayMetrics display = mContext.getResources().getDisplayMetrics();
//        final int width = DensityUtils.dip2px(mContext, 20);
//        int w = display.widthPixels - width;
//        viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT));
        viewHolder.setImageResource(R.id.iv_up, data);
        viewHolder.setChecked(R.id.rb_check, isItemViewToggled(position));
        handleClick(viewHolder, position);
    }

    public void clearSelectedItemViews() {
        mCheckStates.clear();
        notifyDataSetChanged();
    }

    public boolean isItemViewToggled(int position) {
        return mCheckStates.get(position, false);
    }

    public void toggleItemView(int position) {
        switch (choiceMode) {
            case CHOICE_MODE_SINGLE:
                clearSelectedItemViews();
                mCheckStates.put(position, true);
                break;
            case CHOICE_MODE_MULTIPLE:
                if (isItemViewToggled(position)) {
                    mCheckStates.delete(position);
                } else {
                    mCheckStates.put(position, true);
                }
        }

        notifyItemChanged(position);
    }

    public void handleClick(@NotNull final ViewHolder viewHolder, @NotNull final int clickPosition) {
        final View itemView = viewHolder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleItemView(clickPosition);
            }
        });
    }
}
