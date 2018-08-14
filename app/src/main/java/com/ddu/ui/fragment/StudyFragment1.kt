package com.ddu.ui.fragment

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.ddu.R
import com.ddu.db.DbManager
import com.ddu.db.entity.StudyContent
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.AnimatorUtils
import com.ddu.logic.LogicActions
import com.ddu.ui.adapter.StudyContentFragmentPagerAdapter
import com.ddu.ui.fragment.study.StudyTagsFragment
import dalvik.system.DexFile
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_study.*
import java.io.IOException
import java.util.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class StudyFragment1 : DefaultFragment() {
    private var isShow: Boolean = false

    private var mStudyTagsFragment: StudyTagsFragment? = null

    private var mStudyContentFragmentPagerAdapter: StudyContentFragmentPagerAdapter? = null

    private var studyContents: List<StudyContent> = ArrayList()

    private val mTitles = ArrayList<String>()

    companion object {
        fun newInstance(): StudyFragment1 {

            val args = Bundle()

            val fragment = StudyFragment1()
            fragment.arguments = args
            return fragment
        }
    }


    override fun initData(savedInstanceState: Bundle?) {

        mStudyTagsFragment = StudyTagsFragment.newInstance()

        studyContents = DbManager.getStudyContentBox().all

        Observable.fromIterable(studyContents).filter { studyContent -> studyContent.isOld }.subscribe { studyContent -> mTitles.add(studyContent.title) }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_study_t
    }

    override fun initView() {
        mStudyContentFragmentPagerAdapter = StudyContentFragmentPagerAdapter(fragmentManager, mTitles)
        vp_study.setAdapter(mStudyContentFragmentPagerAdapter)
        tl_study.setupWithViewPager(vp_study)
        iv_add_item.setOnClickListener(View.OnClickListener {
            if (!isShow) {
                ObserverManager.getInstance().notify(LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION)
                fl_study.visibility = View.VISIBLE
                ll_study.visibility = View.VISIBLE
                AnimatorUtils.composeIn(fl_study, ll_study, iv_add_item).start()
            } else {
                ObserverManager.getInstance().notify(LogicActions.IC_ADD_ITEM_CLICK_CLOSE_ACTION)
                val animatorSet = AnimatorUtils.composeOut(fl_study, ll_study, iv_add_item)
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        fl_study.visibility = View.INVISIBLE
                        ll_study.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                animatorSet.start()
            }
            isShow = !isShow
        })
        val ft = fragmentManager!!.beginTransaction()

        if (!mStudyTagsFragment!!.isAdded) {
            ft.replace(R.id.fl_study, mStudyTagsFragment)
        }
        ft.commitAllowingStateLoss()
        setTitle("学习")

        //        setFixedTagLayoutOnPageChangeListener();
    }

    // 解决点击tab抖动的bug
    private fun setFixedTagLayoutOnPageChangeListener() {
        try {
            val field = TabLayout::class.java.getDeclaredField("mPageChangeListener")
            field.isAccessible = true
            field.set(this, FixedTagLayoutOnPageChangeListener(tl_study))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    class FixedTagLayoutOnPageChangeListener(tabLayout: TabLayout) : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
        private var isTouchState: Boolean = false

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isTouchState = true
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                isTouchState = false
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (isTouchState) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }
    }

    private fun getClasses(mContext: Context, packageName: String): List<*> {
        val classes = mutableListOf<String>()
        try {
            val packageCodePath = mContext.packageCodePath
            val df = DexFile(packageCodePath)
            val regExp = "^$packageName.\\w+$"
            val iter = df.entries()
            while (iter.hasMoreElements()) {
                val className = iter.nextElement()
                if (className.matches(regExp.toRegex())) {
                    classes.add(className)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return classes
    }
}