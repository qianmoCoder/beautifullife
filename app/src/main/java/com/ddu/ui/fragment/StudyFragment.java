package com.ddu.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ddu.R;
import com.ddu.db.DbManager;
import com.ddu.db.entity.StudyContent;
import com.ddu.icore.common.ObserverManager;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.logic.LogicActions;
import com.ddu.ui.adapter.StudyContentFragmentPagerAdapter;
import com.ddu.ui.fragment.study.StudyTagsFragment;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dalvik.system.DexFile;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by yzbzz on 16/4/6.
 */
public class StudyFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.tl_study)
    TabLayout tlStudy;
    @Nullable
    @BindView(R.id.vp_study)
    ViewPager vpStudy;

    @BindView(R.id.iv_add_item)
    ImageView ivAddItem;

    @BindView(R.id.fl_study)
    FrameLayout flStudy;

    @BindView(R.id.ll_study)
    RelativeLayout llStudy;

    private boolean isShow;

    private StudyTagsFragment mStudyTagsFragment;

    private Unbinder unbinder;

    private StudyContentFragmentPagerAdapter mStudyContentFragmentPagerAdapter;

    private List<StudyContent> studyContents = new ArrayList<>();

    private List<String> mTitles = new ArrayList<>();

    public static StudyFragment newInstance() {

        Bundle args = new Bundle();

        StudyFragment fragment = new StudyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mStudyTagsFragment = StudyTagsFragment.newInstance();

        studyContents = DbManager.getStudyContentDao().loadAll();

        Observable.fromIterable(studyContents).filter(new Predicate<StudyContent>() {
            @Override
            public boolean test(@NonNull StudyContent studyContent) throws Exception {
                return studyContent.getIsOld();
            }
        }).subscribe(new Consumer<StudyContent>() {
            @Override
            public void accept(@NonNull StudyContent studyContent) throws Exception {
                mTitles.add(studyContent.getTitle());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        mStudyContentFragmentPagerAdapter = new StudyContentFragmentPagerAdapter(getFragmentManager(), mTitles);
        vpStudy.setAdapter(mStudyContentFragmentPagerAdapter);
        tlStudy.setupWithViewPager(vpStudy);
        ivAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShow) {
                    ObserverManager.getInstance().notify(LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION);
                    flStudy.setVisibility(View.VISIBLE);
                    llStudy.setVisibility(View.VISIBLE);
                    AnimatorUtils.composeIn(flStudy, llStudy, ivAddItem).start();
                } else {
                    ObserverManager.getInstance().notify(LogicActions.IC_ADD_ITEM_CLICK_CLOSE_ACTION);
                    AnimatorSet animatorSet = AnimatorUtils.composeOut(flStudy, llStudy, ivAddItem);
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            flStudy.setVisibility(View.INVISIBLE);
                            llStudy.setVisibility(View.INVISIBLE);
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

        if (!mStudyTagsFragment.isAdded()) {
            ft.replace(R.id.fl_study, mStudyTagsFragment);
        }
        ft.commitAllowingStateLoss();
        setTitle("学习");

//        setFixedTagLayoutOnPageChangeListener();
    }

    // 解决点击tab抖动的bug
    private void setFixedTagLayoutOnPageChangeListener() {
        try {
            Field field = TabLayout.class.getDeclaredField("mPageChangeListener");
            field.setAccessible(true);
            field.set(this, new FixedTagLayoutOnPageChangeListener(tlStudy));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class FixedTagLayoutOnPageChangeListener extends TabLayout.TabLayoutOnPageChangeListener {
        private boolean isTouchState;

        public FixedTagLayoutOnPageChangeListener(TabLayout tabLayout) {
            super(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isTouchState = true;
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                isTouchState = false;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isTouchState) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private List getClasses(Context mContext, String packageName) {
        ArrayList classes = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                String className = iter.nextElement();
                if (className.matches(regExp)) {
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

}
