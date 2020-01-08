package com.ddu.ui.fragment.study.animation

import android.animation.*
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.view.CircleImageView
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_animator.iv_icon
import kotlinx.android.synthetic.main.fragment_propery_animation.*


/**
 * Created by yzbzz on 16/4/8.
 */
@IElement("AN")
class PropertyAnimationFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_propery_animation
    }

    override fun initView() {
        Glide.with(this).load(R.drawable.more_user_icon).circleCrop().into(iv_icon)

        btn_rotate.setOnClickListener {
            rotate(iv_icon)
        }

        btn_translate.setOnClickListener {
            translate(iv_icon)
        }

        btn_scale.setOnClickListener {
            scale(iv_icon)
        }

        btn_fade.setOnClickListener {
            fade(iv_icon)
        }

        btn_bg_color.setOnClickListener {
            colorizer(iv_icon)
        }

        btn_shower.setOnClickListener {
            shower(iv_icon)
        }
    }

    private fun rotate(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(btn_rotate)
        animator.start()
    }

    private fun translate(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btn_translate)
        animator.start()
    }

    private fun scale(view: View) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btn_scale)
        animator.start()
    }

    private fun fade(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btn_scale)
        animator.start()
    }

    private fun colorizer(view: View) {
        val animator = ObjectAnimator.ofArgb(view.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btn_scale)
        animator.start()
    }

    private fun shower(view: View) {
        val container = view.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW = iv_icon.width.toFloat()
        var starH = iv_icon.height.toFloat()

        val newStar = CircleImageView(mContext)
        newStar.setImageResource(R.drawable.more_user_icon)
        newStar.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2
        container.addView(newStar)

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        mover.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.isEnabled = true
            }
        })
    }


}
