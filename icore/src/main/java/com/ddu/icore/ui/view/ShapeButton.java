package com.ddu.icore.ui.view;

import android.content.Context;
import android.util.AttributeSet;

public class ShapeButton extends ShapeTextView {


    public ShapeButton(Context context) {
        this(context, null);
    }

    public ShapeButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public ShapeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ShapeButton.class.getName();
    }
}
