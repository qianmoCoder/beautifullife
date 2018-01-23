package com.ddu.icore.ui.help;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.ddu.icore.ui.help.IShape.*;

public class ShapeRender {

    private GradientDrawable mGradientDrawable;
    private View mView;

    @IShapeType
    private int mShapeType = DEFAULT;

    @IShapeDirection
    private int mDirection = DIRECTION_LEFT;

    private float[] mRadii;

    private int mRadius;
    private int mColor;

    private ShapeRender(View view, @IShapeType int shapeType) {
        mView = view;
        mShapeType = shapeType;
        mGradientDrawable = new GradientDrawable();
    }

    public ShapeRender setColor(@ColorInt int color) {
        mColor = color;
        return this;
    }

    public ShapeRender setCornerRadiu(int radius) {
        mRadius = radius;
        return this;
    }

    public ShapeRender setOval() {
        mGradientDrawable.setShape(GradientDrawable.OVAL);
        mView.setBackground(mGradientDrawable);
        return this;
    }

    public ShapeRender setDirection(@IShapeDirection int direction) {
        mDirection = direction;
        int height = mView.getMeasuredHeight();
        mGradientDrawable.setCornerRadius(height / 2);
        return this;
    }


    private void setBackground() {
        if (mShapeType == OVAL) {
            mGradientDrawable.setShape(GradientDrawable.OVAL);
        } else if (mShapeType == ROUND) {
            int height = mView.getMeasuredHeight();
            int width = mView.getMeasuredWidth();
            int size = Math.max(height, width);
            mGradientDrawable.setShape(GradientDrawable.OVAL);
            mGradientDrawable.setSize(size, size);
        } else if (mShapeType == ROUND_RECT) {
            int height = mView.getMeasuredHeight();
            mGradientDrawable.setCornerRadius(height / 2);
        } else if (mShapeType == SEGMENT) {

        } else {

        }
        mView.setBackground(mGradientDrawable);
    }

    public ShapeRender setRoundRect(@IShapeDirection int direction) {
        mDirection = direction;
        int height = mView.getMeasuredHeight();
        mRadius = height;
        return this;
    }

    public ShapeRender setLeftTopCornerRadiu(float radius) {
        mRadii[0] = mRadii[1] = radius;
        return this;
    }

    public ShapeRender setRightTopCornerRadiu(float radius) {
        mRadii[2] = mRadii[3] = radius;
        return this;
    }

    public ShapeRender setRightBottomCornerRadiu(float radius) {
        mRadii[4] = mRadii[5] = radius;
        return this;
    }

    public ShapeRender setLeftBottomCornerRadiu(float radius) {
        mRadii[6] = mRadii[7] = radius;
        return this;
    }

    public ShapeRender setCornerRadius(float[] radius) {
        mRadii = radius;
        return this;
    }

    public void render() {
        mGradientDrawable.setColor(mColor);
        mGradientDrawable.setCornerRadius(mRadius);
        mView.setBackground(mGradientDrawable);
    }

    public static ShapeRender with(View view) {
        return new ShapeRender(view, DEFAULT);
    }

    @IntDef({DEFAULT, OVAL, ROUND, ROUND_RECT, SEGMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IShapeType {
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IShapeDirection {
    }

}
