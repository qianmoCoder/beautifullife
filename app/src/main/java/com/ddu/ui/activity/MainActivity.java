package com.ddu.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.R;
import com.ddu.app.App;
import com.ddu.ui.fragment.LifeFragment;
import com.ddu.ui.fragment.MeFragment;
import com.ddu.ui.fragment.StudyFragment;
import com.ddu.ui.fragment.WorkFragment;
import com.ddu.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @Nullable
    @BindView(R.id.fl_home_content)
    FrameLayout flHomeContent;
    @Nullable
    @BindView(R.id.rb_main_study)
    RadioButton rbMainStudy;
    @Nullable
    @BindView(R.id.rb_main_work)
    RadioButton rbMainWork;
    @Nullable
    @BindView(R.id.rb_main_life)
    RadioButton rbMainLife;
    @Nullable
    @BindView(R.id.rb_main_me)
    RadioButton rbMainMe;
    @Nullable
    @BindView(R.id.rg_main)
    RadioGroup rgMain;

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
        rgMain.setOnCheckedChangeListener(this);
        rgMain.check(R.id.rb_main_study);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        add(checkedId);
    }

    private void add(int checkedId) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAll(transaction, mStudyFragment, mWorkFragment, mLifeFragment, mMeFragment);
        switch (checkedId) {
            case R.id.rb_main_study:
                if (null == mStudyFragment) {
                    mStudyFragment = StudyFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mStudyFragment, TAG_STUDY);
                } else {
                    transaction.show(mStudyFragment);
                }
                break;
            case R.id.rb_main_work:
                if (null == mWorkFragment) {
                    mWorkFragment = WorkFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mWorkFragment, TAG_WORK);
                } else {
                    transaction.show(mWorkFragment);
                }
                break;
            case R.id.rb_main_life:
                if (null == mLifeFragment) {
                    mLifeFragment = LifeFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mLifeFragment, TAG_LIFE);
                } else {
                    transaction.show(mLifeFragment);
                }
                break;
            case R.id.rb_main_me:
                if (null == mMeFragment) {
                    mMeFragment = MeFragment.newInstance();
                    transaction.add(flHomeContent.getId(), mMeFragment, TAG_ME);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
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
            ToastUtil.showSuccessToast(R.string.main_exit_msg);
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
}
