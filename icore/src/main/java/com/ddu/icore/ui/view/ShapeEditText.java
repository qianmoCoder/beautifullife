package com.ddu.icore.ui.view;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.ddu.icore.ui.help.ShapeInject;

public class ShapeEditText extends AppCompatEditText {

    private ShapeInject mShapeInject;

    public ShapeEditText(Context context) {
        this(context, null);
    }

    public ShapeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ShapeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        mShapeInject = ShapeInject.inject(this);
        mShapeInject.parseAttributeSet(attrs);
        mShapeInject.background();
    }
}
