package com.ddu.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextPaint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
public class MeFragment extends DefaultFragment {

    final static int COUNTS = 10;//点击次数
    final static long DURATION = 3 * 1000;//规定有效时间
    long[] mHits = new long[COUNTS];

    @Nullable
    @BindView(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Nullable
    @BindView(R.id.et_text)
    EditText etText;

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

    @Nullable
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;

    @Nullable
    @BindView(R.id.tvU)
    TextView tvU;

    private Unbinder unbinder;

    @NonNull
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    private String a = null;

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @CheckResult
    public String generateString(@NonNull String param) {
        return param.replace(" ", "-");
    }

    public String sayHello(@StringRes int resId) {
        return "";
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

        llSetting.setOnClickListener(new View.OnClickListener() {

            long[] mHints = new long[3];

            @Override
            public void onClick(View v) {
//                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                if (mode == Configuration.UI_MODE_NIGHT_YES) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                } else if (mode == Configuration.UI_MODE_NIGHT_NO) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                }
//                baseActivity.recreate();

//                ShareDialogFragment shareDialogFragment = ShareDialogFragment.newInstance();
//                shareDialogFragment.show(getFragmentManager(), "h");
//                startFragment(ToolBarFragment.class);

//                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
//                //获得当前系统已经启动的时间
//                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
//                if (SystemClock.uptimeMillis() - mHints[0] <= 500)
//                    Toast.makeText(mContext, "当你点击三次之后才会出现", Toast.LENGTH_SHORT).show();


                /**
                 * 实现双击方法
                 * src 拷贝的源数组
                 * srcPos 从源数组的那个位置开始拷贝.
                 * dst 目标数组
                 * dstPos 从目标数组的那个位子开始写数据
                 * length 拷贝的元素的个数
                 */
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    String tips = "您已在[" + DURATION + "]ms内连续点击【" + mHits.length + "】次了！！！";
                    Toast.makeText(mContext, tips, Toast.LENGTH_SHORT).show();
                }

            }
        });

        setTitle(R.string.main_tab_me);

//        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(tvU.getText());
//        CharacterStyle span = new StrikethroughSpan();
//        spannableStringBuilder.setSpan(span, tvU.getText().length(), tvU.getText().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        TextPaint paint = new TextPaint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvU.getPaint().

                set(paint);
        etText.setText("");
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
//        App.getMainHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                animation.start();
//            }
//        }, 500);


    }
}
