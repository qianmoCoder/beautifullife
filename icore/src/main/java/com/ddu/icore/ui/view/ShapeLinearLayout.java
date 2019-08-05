package com.ddu.icore.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.ddu.icore.ui.help.ShapeLayoutInject;

public class ShapeLinearLayout extends LinearLayout {

    public ShapeLinearLayout(Context context) {
        this(context, null);
    }

    public ShapeLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ShapeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShapeLayoutInject.inject(this, attrs);
    }
}
