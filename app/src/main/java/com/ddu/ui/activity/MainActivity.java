package com.ddu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.ObserverManager;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.icore.ui.help.BottomNavigationViewHelper;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.icore.util.ToastUtils;
import com.ddu.logic.LogicActions;
import com.ddu.ui.fragment.LifeFragment;
import com.ddu.ui.fragment.MeFragment;
import com.ddu.ui.fragment.StudyFragment;
import com.ddu.ui.fragment.WorkFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Nullable
    @BindView(R.id.fl_home_content)
    FrameLayout flHomeContent;
    @Nullable
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private static final String TAG_STUDY = "TAG_STUDY";
    private static final String TAG_WORK = "TAG_WORK";
    private static final String TAG_LIFE = "TAG_LIFE";
    private static final String TAG_ME = "TAG_ME";

    private StudyFragment mStudyFragment;
    private WorkFragment mWorkFragment;
    private LifeFragment mLifeFragment;
    private MeFragment mMeFragment;

    private FragmentManager mFragmentManager;

    private boolean isExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mStudyFragment = (StudyFragment) mFragmentManager.findFragmentByTag(TAG_STUDY);
            mWorkFragment = (WorkFragment) mFragmentManager.findFragmentByTag(TAG_WORK);
            mLifeFragment = (LifeFragment) mFragmentManager.findFragmentByTag(TAG_LIFE);
            mMeFragment = (MeFragment) mFragmentManager.findFragmentByTag(TAG_ME);
        }

        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_study);
    }

    private void hideAll(@NonNull FragmentTransaction transaction, @NonNull Fragment... fragment) {
        for (Fragment f : fragment) {
            if (f != null) {
                if (!f.isHidden()) {
                    transaction.hide(f);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showToast(R.string.main_exit_msg);
            App.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }


    @Override
    public boolean isShowTitleBar() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAll(transaction, mStudyFragment, mWorkFragment, mLifeFragment, mMeFragment);
        switch (item.getItemId()) {
            case R.id.navigation_study:
                if (null == mStudyFragment) {
                    mStudyFragment = StudyFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mStudyFragment, TAG_STUDY);
                } else {
                    transaction.show(mStudyFragment);
                }
                transaction.commitAllowingStateLoss();
                return true;
            case R.id.navigation_work:
                if (null == mWorkFragment) {
                    mWorkFragment = WorkFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mWorkFragment, TAG_WORK);
                } else {
                    transaction.show(mWorkFragment);
                }
                transaction.commitAllowingStateLoss();
                return true;
            case R.id.navigation_life:
                if (null == mLifeFragment) {
                    mLifeFragment = LifeFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mLifeFragment, TAG_LIFE);
                } else {
                    transaction.show(mLifeFragment);
                }
                transaction.commitAllowingStateLoss();
                return true;
            case R.id.navigation_me:
                if (null == mMeFragment) {
                    mMeFragment = MeFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mMeFragment, TAG_ME);
                } else {
                    transaction.show(mMeFragment);
                }
                transaction.commitAllowingStateLoss();
                return true;
        }
        return false;
    }

    @Override
    public void registerObserver() {
        ObserverManager.getInstance().registerObserver(LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION, this);
        ObserverManager.getInstance().registerObserver(LogicActions.IC_ADD_ITEM_CLICK_CLOSE_ACTION, this);
    }

    @Override
    public void onReceiverNotify(GodIntent godIntent) {
        int action = godIntent.getAction();
        if (action == LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION) {
            navigation.setVisibility(View.GONE);
//            doAnimator(navigation, true);
        } else {
//            doAnimator(navigation, false);
            navigation.setVisibility(View.VISIBLE);
        }
    }

    private void doAnimator(final View view, final boolean isClose) {
        if (!isClose) {
            view.setVisibility(View.VISIBLE);
        }
        int height = view.getHeight();
        ObjectAnimator translationY = AnimatorUtils.translationY(view, 300, isClose ? 0 : height, isClose ? height : 0);
        ObjectAnimator alpha = AnimatorUtils.alpha(view, 300, isClose ? 1 : 0, isClose ? 0 : 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(translationY, alpha);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isClose) {
                    view.setVisibility(View.GONE);
                }
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
}
