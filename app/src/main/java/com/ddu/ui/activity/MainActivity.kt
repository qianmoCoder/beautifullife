package com.ddu.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.ddu.R
import com.ddu.icore.aidl.GodIntent
import com.ddu.icore.app.BaseApp
import com.ddu.icore.common.ObserverManager
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.icore.ui.help.BottomNavigationViewHelper
import com.ddu.icore.util.AnimatorUtils
import com.ddu.icore.util.ToastUtils
import com.ddu.logic.LogicActions
import com.ddu.ui.fragment.LifeFragment
import com.ddu.ui.fragment.MeFragment
import com.ddu.ui.fragment.StudyFragment
import com.ddu.ui.fragment.WorkFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var mStudyFragment: StudyFragment? = null
    private var mWorkFragment: WorkFragment? = null
    private var mLifeFragment: LifeFragment? = null
    private var mMeFragment: MeFragment? = null

    private var isExit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            mStudyFragment = supportFragmentManager.findFragmentByTag(TAG_STUDY)?.let { it as StudyFragment }
            mWorkFragment = supportFragmentManager.findFragmentByTag(TAG_WORK) as WorkFragment
            mLifeFragment = supportFragmentManager.findFragmentByTag(TAG_LIFE) as LifeFragment
            mMeFragment = supportFragmentManager.findFragmentByTag(TAG_ME) as MeFragment
        }

        BottomNavigationViewHelper.disableShiftMode(navigation)
        navigation.setOnNavigationItemSelectedListener(this)
        navigation.selectedItemId = R.id.navigation_study
//        startActivity<MainActivityT>("id" to 5)
    }

    private fun hideAll(transaction: FragmentTransaction, vararg fragment: Fragment?) {
//        fragment?.filter {
//            it?.isHidden ?: false
//        }.map {
//            transaction.hide(it)
//        }

        for (f in fragment) {
            if (f != null) {
                if (!f.isHidden) {
                    transaction.hide(f)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if (!isExit) {
            isExit = true
            ToastUtils.showToast(R.string.main_exit_msg)
            BaseApp.postDelayed(Runnable { isExit = false }, 2000)
        } else {
            finish()
        }
    }


    override fun isShowTitleBar(): Boolean {
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        hideAll(transaction, mStudyFragment, mWorkFragment, mLifeFragment, mMeFragment)
        when (item.itemId) {
            R.id.navigation_study -> {
                if (null == mStudyFragment) {
                    mStudyFragment = StudyFragment.newInstance()
                    transaction.add(fl_home_content!!.getId(), mStudyFragment, TAG_STUDY)
                } else {
                    transaction.show(mStudyFragment)
                }
                transaction.commitAllowingStateLoss()
                return true
            }
            R.id.navigation_work -> {
                if (null == mWorkFragment) {
                    mWorkFragment = WorkFragment.newInstance()
                    transaction.add(fl_home_content!!.getId(), mWorkFragment, TAG_WORK)
                } else {
                    transaction.show(mWorkFragment)
                }
                transaction.commitAllowingStateLoss()
                return true
            }
            R.id.navigation_life -> {
                if (null == mLifeFragment) {
                    mLifeFragment = LifeFragment.newInstance()
                    transaction.add(fl_home_content!!.getId(), mLifeFragment, TAG_LIFE)
                } else {
                    transaction.show(mLifeFragment)
                }
                transaction.commitAllowingStateLoss()
                return true
            }
            R.id.navigation_me -> {
                if (null == mMeFragment) {
                    mMeFragment = MeFragment.newInstance()
                    transaction.add(fl_home_content!!.getId(), mMeFragment, TAG_ME)
                } else {
                    transaction.show(mMeFragment)
                }
                transaction.commitAllowingStateLoss()
                return true
            }
        }
        return false
    }

    override fun registerObserver() {
        ObserverManager.getInstance().registerObserver(LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION, this)
        ObserverManager.getInstance().registerObserver(LogicActions.IC_ADD_ITEM_CLICK_CLOSE_ACTION, this)
    }

    override fun onReceiverNotify(godIntent: GodIntent) {
        val action = godIntent.getAction()
        if (action == LogicActions.IC_ADD_ITEM_CLICK_OPEN_ACTION) {
            navigation!!.visibility = View.GONE
            //            doAnimator(navigation, true);
        } else {
            //            doAnimator(navigation, false);
            navigation!!.visibility = View.VISIBLE
        }
    }

    private fun doAnimator(view: View, isClose: Boolean) {
        if (!isClose) {
            view.visibility = View.VISIBLE
        }
        val height = view.height.toFloat()

        val translationY = AnimatorUtils.translationY(view, 300, if (isClose) 0f else height, if (isClose) height else 0f)
        val alpha = AnimatorUtils.alpha(view, 300, if (isClose) 1f else 0f, if (isClose) 0f else 1f)
        val animatorSet = AnimatorSet()
        animatorSet.duration = 3000
        animatorSet.playTogether(translationY, alpha)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                if (isClose) {
                    view.visibility = View.GONE
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })
        animatorSet.start()
    }

    companion object {
        private val TAG_STUDY = "TAG_STUDY"
        private val TAG_WORK = "TAG_WORK"
        private val TAG_LIFE = "TAG_LIFE"
        private val TAG_ME = "TAG_ME"
    }
}