package com.ddu.icore.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ddu.icore.ui.help.ShapeInject;

public class ShapeImageView extends AppCompatImageView {

    private ShapeInject mShapeInject;

    public ShapeImageView(Context context) {
        this(context, null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        mShapeInject = new ShapeInject(this);
        mShapeInject.init(attrs, false);
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
