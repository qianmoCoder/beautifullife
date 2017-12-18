package com.ddu.icore.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by yzbzz on 2017/12/14.
 */

public class AbsRecycleView extends RecyclerView {


    public AbsRecycleView(Context context) {
        super(context);
    }

    public AbsRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
