package com.ddu.icore.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by yzbzz on 2017/8/28.
 */

public class NovaTextView extends AppCompatTextView {

    public NovaTextView(Context context) {
        super(context);
    }

    public NovaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NovaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        Log.v("lhz", "NovaTextView: " + getClass().getName());
        super.setOnClickListener(l);
    }
}
