package com.ddu.icore.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ddu.icore.ui.help.ShapeInject;

/**
 * Created by yzbzz on 2017/4/21.
 */

public class ShapeView extends View {

    private ShapeInject mShapeInject;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        mShapeInject = new ShapeInject(this);
        mShapeInject.init(attrs, true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int type = mShapeInject.getShapeType();
        if (type == ShapeInject.ROUND) {
            mShapeInject.setRound();
        } else if (type == ShapeInject.ROUND_RECT) {
            mShapeInject.setRoundRect();
        } else if (type == ShapeInject.SEGMENT) {
            mShapeInject.setSegmented();
        } else if (type == ShapeInject.OVAL) {
            mShapeInject.setOval();
        }
    }

    public ShapeInject getShapeInject() {
        return mShapeInject;
    }

    public void setShapeInject(ShapeInject shapeInject) {
        this.mShapeInject = shapeInject;
    }
}
