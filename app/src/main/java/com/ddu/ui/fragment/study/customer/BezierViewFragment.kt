package com.ddu.ui.fragment.study.customer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.PointF
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.ddu.R
import com.ddu.icore.bezier.QuadraticPointFTypeEvaluator
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.widget.MoveButton
import com.iannotation.IElement
import kotlinx.android.synthetic.main.study_bezier.*

/**
 * Created by yzbzz on 2018/6/8.
 */
@IElement("customer")
class BezierViewFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.study_bezier
    }

    override fun initView() {

        btn_shake.setOnClickListener {
            startBezier()

        }
    }

    private fun startBezier() {
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val mAnimCoinWidth = btn_start.width
        val mAnimCoinHeight = btn_start.height

        val start = IntArray(2)
        btn_start.getLocationInWindow(start)
        start[0] = btn_start.x.toInt()
        start[1] = btn_start.y.toInt()

        val control = IntArray(2)
        btn_control.getLocationInWindow(control)
        control[0] = btn_control.x.toInt() + btn_control.width / 2
        control[1] = btn_control.y.toInt() + btn_control.height / 2

        val end = IntArray(2)
        btn_end.getLocationInWindow(end)
        end[0] = btn_end.x.toInt()
        end[1] = btn_end.y.toInt()

        val button = MoveButton(mContext)
        val layoutParams = FrameLayout.LayoutParams(btn_start.width, btn_start.height)
        button.layoutParams = layoutParams
        button.x = start[0].toFloat()
        button.y = start[1].toFloat()
//        button.x = width / 2f - mAnimCoinWidth / 2f
//        button.y = height / 2f - mAnimCoinHeight / 2f

        rl_root.addView(button)

        val startP = PointF()
        val endP = PointF()
        val controlP = PointF()

        startP.x = start[0].toFloat()
        startP.y = start[1].toFloat()

        endP.x = end[0].toFloat()
        endP.y = end[1].toFloat()

        controlP.x = control[0].toFloat()
        controlP.y = control[1].toFloat()

//        startP.x = btn_start.x
//        startP.y = btn_start.y
//        startP.x = width / 2f - mAnimCoinWidth / 2f
//        startP.y = height / 2f - mAnimCoinHeight / 2f

//        endP.x = btn_end.x
//        endP.y = btn_end.y

//        endP.x = end[0] + width / 2f - mAnimCoinWidth / 2f
//        endP.y = end[1] + height / 2f - mAnimCoinHeight / 2f
//
//        controlP.x = (end[0] - mAnimCoinWidth).toFloat()
//        controlP.y = (startP.y - endP.y) / 2



        val objectAnimator =
            ObjectAnimator.ofObject(button, "mPointF", QuadraticPointFTypeEvaluator(controlP), startP, endP)
//        objectAnimator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(animation: Animator?) {
//            }
//
//            override fun onAnimationEnd(animation: Animator?) {
//                rl_root.removeView(button)
//            }
//
//            override fun onAnimationCancel(animation: Animator?) {
//            }
//
//            override fun onAnimationStart(animation: Animator?) {
//            }
//
//        })
//        objectAnimator.start()
//        val valueAnimator = ValueAnimator.ofObject(PointFTypeEvaluator(controlP), startP, endP)
//        valueAnimator.addUpdateListener {
//            val point = it.animatedValue as PointF
//            button.x = point.x
//            button.y = point.y
//        }

        val objectAnimator2 = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0f)
        val objectAnimator3 = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, objectAnimator2, objectAnimator3)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.duration = 700
        animatorSet.startDelay = (100 * 1).toLong()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                rl_root.removeView(button)
            }

        })

        animatorSet.start()
    }
}
