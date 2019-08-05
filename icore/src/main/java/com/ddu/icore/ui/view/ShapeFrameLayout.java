package com.ddu.icore.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.ddu.icore.ui.help.ShapeLayoutInject;

public class ShapeFrameLayout extends LinearLayout {

    public ShapeFrameLayout(Context context) {
        this(context, null);
    }

    public ShapeFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ShapeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShapeLayoutInject.inject(this, attrs);
    }
}
