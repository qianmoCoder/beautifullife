package com.ddu.ui.fragment.study.ui

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.AnimatorUtils
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_design.*

/**
 * Created by lhz on 16/5/6.
 */
@Element("UI")
class DesignFragment : DefaultFragment() {

    private var mDrawFragment: ShapeInjectFragment? = null

    private var animEnter: Animation? = null
    private var animExit: Animation? = null

    private var isShow: Boolean = false

    override fun initData(savedInstanceState: Bundle?) {
        mDrawFragment = ShapeInjectFragment.newInstance("1")
        animEnter = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_to_bottom)
        animEnter!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        animExit = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_to_top)
        animExit!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                fl_content.visibility = View.INVISIBLE
                ll_content.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_design
    }

    override fun initView() {
        val adapter = SampleFragmentPagerAdapter(baseActivity.supportFragmentManager, baseActivity)
        vp_design!!.adapter = adapter
        tl_design!!.setupWithViewPager(vp_design)
        tl_design!!.tabMode = TabLayout.MODE_SCROLLABLE

        add_channel_iv!!.setOnClickListener {
            //                performAnimateOn();
            //                AnimatorUtils.translationY(fl_content, 300, -fl_content.getHeight(), 0).start();
            if (!isShow) {
                fl_content!!.visibility = View.VISIBLE
                ll_content!!.visibility = View.VISIBLE
                AnimatorUtils.composeIn(fl_content, ll_content, add_channel_iv).start()
            } else {
                val animatorSet = AnimatorUtils.composeOut(fl_content, ll_content, add_channel_iv)
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                        fl_content!!.visibility = View.INVISIBLE
                        ll_content!!.visibility = View.INVISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
                animatorSet.start()
            }
            isShow = !isShow
        }

        val ft = fragmentManager!!.beginTransaction()

        if (!mDrawFragment!!.isAdded) {
            ft.replace(R.id.fl_content, mDrawFragment)
        }
        ft.commitAllowingStateLoss()

        ll_content!!.setOnTouchListener { v, event -> true }
    }

    inner class SampleFragmentPagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
        internal val PAGE_COUNT = 10
        private val tabTitles = arrayOf("缴费", "入场", "更多", "1", "更多", "更多", "更多", "更多", "更多", "更多")

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getItem(position: Int): Fragment {
            return SwipeRefreshFragment.newInstance("")
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }
    }

    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        fun newInstance(taskId: String): DesignFragment {
            val arguments = Bundle()
            arguments.putString("ARGUMENT_TASK_ID", taskId)
            val fragment = DesignFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
