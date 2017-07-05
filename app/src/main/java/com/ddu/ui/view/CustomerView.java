package com.ddu.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.internal.LoadingView;
import com.ddu.icore.util.AnimatorUtils;

/**
 * Created by yzbzz on 2017/5/8.
 */

public class CustomerView extends LoadingView {

    private ImageView mImageView;
    private ImageView ivG;

    private AnimatorSet carAnimatorSet;
    private ObjectAnimator objectAnimator;
    private ObjectAnimator scaleByObjectAnimator;

    private ObjectAnimator ivgAnimator;

    private Matrix mHeaderImageMatrix;
    private float mRotationPivotX, mRotationPivotY;

    public CustomerView(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase pullToRefreshBase) {
        super(context, mode, pullToRefreshBase.getPullToRefreshScrollDirection());

    }

    public void setCarImageView(ImageView imageView) {
        this.mImageView = imageView;
        scaleByObjectAnimator = AnimatorUtils.scaleByObjectAnimator(imageView, 300, 0f, 1f);
        objectAnimator = AnimatorUtils.scaleY(imageView, 300, true, 1.1f, 1f, 1.1f);

        carAnimatorSet = new AnimatorSet();
        carAnimatorSet.playSequentially(scaleByObjectAnimator, objectAnimator);
    }

    public void setIvG(ImageView imageView) {
        this.ivG = imageView;

        ivG.setScaleType(ImageView.ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        ivG.setImageMatrix(mHeaderImageMatrix);

        mRotationPivotX = Math.round(getDrawable().getIntrinsicWidth() / 2f);
        mRotationPivotY = Math.round(getDrawable().getIntrinsicHeight() / 2f);

        ivgAnimator = AnimatorUtils.rotationY(ivG, 500, 0, 60);
        ivgAnimator.setStartDelay(150);
    }

    @Override
    public View getLoadingView() {
        return mLayoutInflater.inflate(R.layout.fragment_refresh, this);
    }


    public void onPull(float scale) {
        float angle = Math.max(0f, Math.min(60f, scale * 60f));
        AnimatorUtils.rotationY(ivG, 0, 60, 60 - angle).start();
    }

    public void refreshing() {

        mImageView.setVisibility(VISIBLE);
        if (null != carAnimatorSet) {
            mImageView.setPivotX(mImageView.getWidth() / 2);
            mImageView.setPivotY(mImageView.getHeight() - 5);
            carAnimatorSet.start();

        }
        ivG.setPivotX(ivG.getWidth());
        ivG.setPivotY(ivG.getHeight() / 2);
        ivgAnimator.start();
    }

    public void releaseToRefresh() {
    }

    public void reset() {
        mImageView.setVisibility(INVISIBLE);

        objectAnimator.cancel();
        scaleByObjectAnimator.cancel();

        carAnimatorSet.cancel();

        ivgAnimator.cancel();

        mHeaderImageMatrix.reset();
        ivG.setImageMatrix(mHeaderImageMatrix);
    }

    public void pullToRefresh() {
    }

    private Drawable getDrawable() {
        return getResources().getDrawable(R.drawable.ptf_g);
    }
}
