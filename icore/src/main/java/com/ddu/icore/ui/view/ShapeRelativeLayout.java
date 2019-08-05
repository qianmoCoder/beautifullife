package com.ddu.icore.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.ddu.icore.ui.help.ShapeLayoutInject;

public class ShapeRelativeLayout extends RelativeLayout {

    public ShapeRelativeLayout(Context context) {
        this(context, null);
    }

    public ShapeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ShapeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ShapeLayoutInject.inject(this, attrs);
    }

}
