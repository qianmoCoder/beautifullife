package com.ddu.icore.ui.help;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ShapeInjectHelper {

    public static final int OVAL = 0x01;
    public static final int ROUND = 0x02;
    public static final int ROUND_RECT = 0x04;
    public static final int SEGMENT = 0x08;

    public static final int DIRECTION_LEFT = 0x0010;
    public static final int DIRECTION_RIGHT = 0x0020;
    public static final int DIRECTION_TOP = 0x0040;
    public static final int DIRECTION_BOTTOM = 0x0080;


    private Drawable mBackground;
    private ColorDrawable mColorDrawable;

    private GradientDrawable mNormalBackground;
    private GradientDrawable mPressedBackground;
    private GradientDrawable mDisableBackground;

    //radius
    private float mRadius = 0;

    private
    @IShapeDirection
    int direction = DIRECTION_LEFT;


    //background color
    private int mNormalBackgroundColor = 0;
    private int mPressedBackgroundColor = 0;
    private int mDisableBackgroundColor = 0;


    private int[][] states;

    StateListDrawable mStateBackground;

    private View mView;

    private int shapeTypeNull = 0x00;
    private
    @IShapeType
    int shapeType = shapeTypeNull;

    public ShapeInjectHelper(View view) {

        mView = view;

        mBackground = mView.getBackground();

        mNormalBackground = new GradientDrawable();
        mPressedBackground = new GradientDrawable();
        mDisableBackground = new GradientDrawable();

        states = new int[4][];

        mStateBackground = new StateListDrawable();

        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_enabled};

        if (mBackground instanceof ColorDrawable) {
            mColorDrawable = (ColorDrawable) mBackground;
        }
        int defValue;
        if (null != mColorDrawable) {
            defValue = mColorDrawable.getColor();
            mDisableBackgroundColor = mPressedBackgroundColor = mNormalBackgroundColor = defValue;
            mNormalBackground.setColor(mNormalBackgroundColor);
            mPressedBackground.setColor(mPressedBackgroundColor);
            mDisableBackground.setColor(mDisableBackgroundColor);
        }
    }

    public ShapeInjectHelper backgroundColor(int argb) {
        mNormalBackground.setColor(argb);
        mDisableBackground.setColor(argb);
        mPressedBackground.setColor(argb);
        return this;
    }

    public ShapeInjectHelper radius(float radius) {
        mNormalBackground.setCornerRadius(radius);
        mPressedBackground.setCornerRadius(radius);
        mDisableBackground.setCornerRadius(radius);
        return this;
    }

    public ShapeInjectHelper strokeWidth(int width, int color, float dashWidth, float dashGap) {
        mNormalBackground.setStroke(width, color, dashWidth, dashGap);
        mPressedBackground.setStroke(width, color, dashWidth, dashGap);
        mDisableBackground.setStroke(width, color, dashWidth, dashGap);
        return this;
    }

    public ShapeInjectHelper strokeWidth(int width, int color) {
        strokeWidth(width, color, 0, 0);
        return this;
    }


    public ShapeInjectHelper shapeType(@IShapeType int shapeType) {
        this.shapeType = shapeType;
        return this;
    }

    public ShapeInjectHelper shapeDirection(@IShapeDirection int direction) {
        this.direction = direction;
        return this;
    }

    private ShapeInjectHelper setRadius(float[] radii) {
        mNormalBackground.setCornerRadii(radii);
        mPressedBackground.setCornerRadii(radii);
        mDisableBackground.setCornerRadii(radii);
        return this;
    }

    public ShapeInjectHelper setBackground() {
        if (null != mView) {
            int height = mView.getMeasuredHeight();
            int width = mView.getMeasuredWidth();
            int size = Math.max(height, width);
            if (shapeType == OVAL) {
                setOval();
            } else if (shapeType == ROUND) {
                setRound(size);
            } else if (shapeType == ROUND_RECT) {
                setRoundRect();
            } else if (shapeType == SEGMENT) {
                setSegmented();
            }
        }
        mStateBackground.addState(states[0], mPressedBackground);
        mStateBackground.addState(states[1], mPressedBackground);
        mStateBackground.addState(states[3], mDisableBackground);
        mStateBackground.addState(states[2], mNormalBackground);
        mView.setBackground(mStateBackground);
        return this;
    }

    private void setSegmented() {
        setSegmented(direction);
    }

    private void setSegmented(final int direction) {
        float radius = mView.getMeasuredHeight();
        float[] radii;
        if (direction == DIRECTION_TOP) {
            radii = new float[]{radius, radius, radius, radius, 0, 0, 0, 0};
        } else if (direction == DIRECTION_BOTTOM) {
            radii = new float[]{0, 0, 0, 0, radius, radius, radius, radius};
        } else if (direction == DIRECTION_RIGHT) {
            radii = new float[]{0, 0, radius, radius, radius, radius, 0, 0};
        } else {
            radii = new float[]{radius, radius, 0, 0, 0, 0, radius, radius};
        }
        setRadius(radii);
    }


    private void setRadius(@FloatRange(from = 0) float radius) {
        this.mRadius = radius;
        mNormalBackground.setCornerRadius(mRadius);
        mPressedBackground.setCornerRadius(mRadius);
        mDisableBackground.setCornerRadius(mRadius);
    }

    private void setRoundRect() {
        int height = mView.getMeasuredHeight();
        setRadius(height / 2f);
    }

    private void setOval() {
        mPressedBackground.setShape(GradientDrawable.OVAL);
        mDisableBackground.setShape(GradientDrawable.OVAL);
        mNormalBackground.setShape(GradientDrawable.OVAL);
    }

    private void setRound() {
        int height = mView.getMeasuredHeight();
        int width = mView.getMeasuredWidth();
        int size = Math.max(height, width);
        setRound(size);
    }

    private void setRound(int size) {
        setRound(size, mNormalBackground, mPressedBackground, mDisableBackground);
    }

    private void setRound(int size, GradientDrawable... gradientDrawables) {
        for (GradientDrawable gradientDrawable : gradientDrawables) {
            gradientDrawable.setShape(GradientDrawable.OVAL);
            gradientDrawable.setSize(size, size);
        }
    }


    @IntDef({OVAL, ROUND, ROUND_RECT, SEGMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IShapeType {
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_TOP, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IShapeDirection {
    }

}
