package com.ddu.ui.fragment.study.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class DesignFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.tl_design)
    TabLayout tlLifeMortgage;
    @Nullable
    @BindView(R.id.vp_design)
    ViewPager vpLifeMortgage;

    @BindView(R.id.add_channel_iv)
    ImageView addChannelIv;

    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private Unbinder unbinder;

    private DrawFragment mDrawFragment;

    private Animation animEnter = null;
    private Animation animExit = null;

    private boolean isShow;

    @NonNull
    public static DesignFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString("ARGUMENT_TASK_ID", taskId);
        DesignFragment fragment = new DesignFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mDrawFragment = DrawFragment.newInstance("1");
        animEnter = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_to_bottom);
        animEnter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animExit = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_to_top);
        animExit.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flContent.setVisibility(View.INVISIBLE);
                llContent.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_design;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(baseActivity.getSupportFragmentManager(), baseActivity);
        vpLifeMortgage.setAdapter(adapter);
        tlLifeMortgage.setupWithViewPager(vpLifeMortgage);
        tlLifeMortgage.setTabMode(TabLayout.MODE_SCROLLABLE);

        addChannelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                performAnimateOn();
//                AnimatorUtils.translationY(flContent, 300, -flContent.getHeight(), 0).start();
                if (!isShow) {
                    flContent.setVisibility(View.VISIBLE);
                    llContent.setVisibility(View.VISIBLE);
                    AnimatorUtils.composeIn(flContent, llContent, addChannelIv).start();
                } else {
                    AnimatorSet animatorSet = AnimatorUtils.composeOut(flContent, llContent, addChannelIv);
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flContent.setVisibility(View.INVISIBLE);
                            llContent.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animatorSet.start();
                }
                isShow = !isShow;
            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (!mDrawFragment.isAdded()) {
            ft.replace(R.id.fl_content, mDrawFragment);
        }
        ft.commitAllowingStateLoss();

        llContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 10;
        @NonNull
        private String tabTitles[] = new String[]{"缴费", "入场", "更多", "1", "更多", "更多", "更多", "更多", "更多", "更多"};
        private Context context;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return SwipeRefreshFragment.newInstance("");
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
