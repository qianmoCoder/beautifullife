package com.ddu.ui.fragment.study.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.ddu.R
import com.ddu.icore.callback.Consumer
import com.ddu.icore.common.ext.awaitEnd
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.util.SystemUtils
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_animation_alpha.*
import kotlinx.coroutines.launch

/**
 * Created by yzbzz on 16/4/8.
 */
@IElement("AN")
class AlphaAnimationFragment : DefaultFragment() {

    private lateinit var objectAnimatorUP: ObjectAnimator
    private lateinit var objectAnimatorDOWN: ObjectAnimator

    private var consumer: Consumer? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_animation_alpha
    }

    override fun initView() {
        val alphaIn = AnimationUtils.loadAnimation(ctx, R.anim.alpha_in)
        val alphaOut = AnimationUtils.loadAnimation(ctx, R.anim.alpha_out)
        tv_alpha_in.setOnClickListener {

            tv_alpha_in.startAnimation(alphaIn)

            alphaIn.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    Log.v("lhz", "onAnimationEnd: ")
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
            Log.v("lhz", "begin: ")
        }
        tv_alpha_out.setOnClickListener {
//            tv_alpha_out.startAnimation(alphaOut)
            showAnimator(object : Consumer {
                override fun accept() {
                    startUp()
                }
            })
        }

        tv_animator.setOnClickListener {

        }

        objectAnimatorUP = ObjectAnimator.ofFloat(tv_animator, View.TRANSLATION_Y, 500f, 0f)
        objectAnimatorUP.duration = 500

        objectAnimatorDOWN = ObjectAnimator.ofFloat(tv_animator, View.TRANSLATION_Y, 0f, 500f)
        objectAnimatorDOWN.duration = 500
    }

    private fun showAnimator(consumer1: Consumer) {
        Log.v("lhz", "showAnimator: ")
        this.consumer = consumer1
        if (tv_animator.visibility == View.VISIBLE) {
            lifecycleScope.launch {
                if (objectAnimatorDOWN.isRunning) {
                    objectAnimatorDOWN.cancel()
                } else {
                    startDown()
                }
                consumer?.accept()
            }
        } else {
            consumer?.accept()
        }
    }

    private fun startUp() {
        tv_animator.visibility = View.VISIBLE
        objectAnimatorUP.start()
        Log.v("lhz", "startUp: " + System.currentTimeMillis())
        tv_animator.handler.postDelayed({
            lifecycleScope.launch {
                startDown()
            }
        }, 1000)
    }

    private suspend fun startDown() {
        objectAnimatorDOWN.start()
        objectAnimatorDOWN.awaitEnd()
        tv_animator.visibility = View.GONE
        Log.v("lhz", "onAnimationDownEnd-startDown: ")
    }

}
