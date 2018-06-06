package com.ddu.ui.fragment.study.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;
import com.iannotation.Element;

/**
 * Created by yzbzz on 2017/5/25.
 */
@Element("UI")
public class DialogFragment extends DefaultFragment {

    private Button btnUI;
    private Button btnBottomDialog;

    private ImageView mIvRotate;

    private ImageView mIvCar;
    private ImageView mIvG;

    private ObjectAnimator objectAnimator;
    private ObjectAnimator rotationY;
    private ObjectAnimator scaleByObjectAnimator;
    private AnimatorSet animatorSet;

    private Matrix mHeaderImageMatrix;

    private float mRotationPivotX, mRotationPivotY;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_dialog;
    }

    @Override
    public void initView() {
        mIvRotate = findViewById(R.id.iv_rotate);
//        mIvRotate.setScaleType(ImageView.ScaleType.CENTER);
//        mIvRotate.setImageDrawable(getDrawable());

        mRotationPivotX = Math.round(getDrawable().getIntrinsicWidth());
        mRotationPivotY = Math.round(getDrawable().getIntrinsicHeight() / 2f);

        mIvRotate.setScaleType(ImageView.ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        mIvRotate.setImageMatrix(mHeaderImageMatrix);

        btnUI = findViewById(R.id.btn_dialog);
        mIvCar = findViewById(R.id.iv_car);
        mIvG = findViewById(R.id.iv_g);

        btnBottomDialog = findViewById(R.id.btn_bottom_dialog);
        btnBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvCar.setVisibility(View.VISIBLE);
                mIvCar.setPivotX(mIvCar.getWidth() / 2);
                mIvCar.setPivotY(mIvCar.getHeight());
                mIvG.setPivotX(mIvG.getWidth());
                mIvG.setPivotY(mIvG.getHeight() / 2);
                animatorSet.start();

                rotationY.start();
            }
        });
        btnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mHeaderImageMatrix.setRotate(60, mRotationPivotX, mRotationPivotY);
                mIvRotate.setImageMatrix(mHeaderImageMatrix);

//                mIvCar.setVisibility(View.INVISIBLE);
//                scaleByObjectAnimator.cancel();
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("我是标题");
//                builder.setMessage("当文字eiwrowejowoejjwoidosjsoie");
//                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance();
//                        bottomSheetDialog.show(getFragmentManager(),"bottomSheetDialog");
//                    }
//                });
//                builder.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.create().show();
            }
        });
        initAnimator();
    }

    private Drawable getDrawable() {
        return getResources().getDrawable(R.drawable.ptf_g);
    }

    private void initAnimator() {
        scaleByObjectAnimator = AnimatorUtils.scaleByObjectAnimator(mIvCar, 500, 0f, 1f);
        objectAnimator = AnimatorUtils.scaleY(mIvCar, 500, true, 1.1f, 1f, 1.1f);
        rotationY = AnimatorUtils.rotationY(mIvG, 500, 0, 60);
        rotationY.setStartDelay(300);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(scaleByObjectAnimator, objectAnimator);


    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
