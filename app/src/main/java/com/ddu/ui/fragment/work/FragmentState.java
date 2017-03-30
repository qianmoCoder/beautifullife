package com.ddu.ui.fragment.work;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/4/6.
 */
public class FragmentState extends DefaultFragment {

    @Nullable
    @BindView(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;

//    @Nullable
//    @BindView(R.id.unread_number_tip)
//    DropFake dropFake;
//    @Nullable
//    @BindView(R.id.unread_cover)
//    DropCover dropCover;

    @Nullable
    @BindView(R.id.iv_guide_hand)
    ImageView ivGuideHand;
    @Nullable
    @BindView(R.id.ll_guide)
    LinearLayout llGuideDelete;

    @Nullable
    @BindView(R.id.iv_guide_delete)
    ImageView ivGuideDelete;

    private Unbinder unbinder;

    @NonNull
    public static FragmentState newInstance() {
        FragmentState fragment = new FragmentState();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
//        dropFake.setText("3");
//        dropFake.setClickListener(new DropFake.ITouchListener() {
//            @Override
//            public void onDown() {
//                DropManager.getInstance().setCurrentId("0");
//                DropManager.getInstance().getDropCover().down(dropFake, "3");
//            }
//
//            @Override
//            public void onMove(float curX, float curY) {
//                DropManager.getInstance().getDropCover().move(curX, curY);
//            }
//
//            @Override
//            public void onUp() {
//                DropManager.getInstance().getDropCover().up();
//            }
//        });
//
//        DropManager.getInstance().init(mContext, dropCover, new DropCover.IDropCompletedListener() {
//            @Override
//            public void onCompleted(Object id, boolean explosive) {
//
//            }
//        });

        setDefaultTitle("我");
    }

    @OnClick(R.id.rl_person_info)
    public void onClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String title = "today";
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            startActivity(chooser);
        }
//        int color = getResources().getColor(R.color.c_4897fa);
//        int c = Color.parseColor("#4897fa");
//        Log.v("lhz","color: " + color);
//        Log.v("lhz", "c: " + c);
//        animator();
//        Bundle bundle = new Bundle();
//        bundle.putString("title", "163");
//        bundle.putString("url", "http://www.163.com");
//        baseActivity.startFragment(WebFragment.class, bundle);
//        DialogUtils.createLoadingDialog(mContext).show();
//        DialogUtils.showDialog(mContext, R.drawable.toast_right_icon, "Hello", "OK", new DialogUtils.ITwoButtonListener() {
//            @Override
//            public void onBtnCancelClickListener(Dialog dialog) {
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onBtnClickListener(Dialog dialog) {
//                dialog.dismiss();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @NonNull
    private ObjectAnimator[] objectAnimator = new ObjectAnimator[2];

    private void animator() {
        ivGuideDelete.setVisibility(View.VISIBLE);
        objectAnimator[0] = AnimatorUtils.translation(ivGuideHand, 1500, 0, -100);
        objectAnimator[1] = AnimatorUtils.translation(ivGuideDelete, 1500, 100, 0);
        final AnimatorSet animation = new AnimatorSet();
        animation.playTogether(objectAnimator);
        animation.start();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivGuideDelete.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        ivGuideDelete.setVisibility(View.INVISIBLE);
//        App.getMainThreadHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animation.start();
//            }
//        }, 500);


    }
}
