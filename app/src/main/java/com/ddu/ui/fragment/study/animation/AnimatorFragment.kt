package com.ddu.ui.fragment.study.animation

import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import com.bumptech.glide.Glide
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_animator.*

/**
 * Created by yzbzz on 16/4/8.
 */
@IElement("AN")
class AnimatorFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_animator
    }

    override fun initView() {
        Glide.with(this).load(R.drawable.more_user_icon).circleCrop().into(iv_icon)

        val valueAnimator =
            AnimatorInflater.loadAnimator(ctx, R.animator.value_animator) as ValueAnimator
        valueAnimator.addUpdateListener {
            iv_icon.y = it.animatedValue as Float
        }

        val objectAnimator = AnimatorInflater.loadAnimator(ctx, R.animator.object_animator) as ObjectAnimator
        objectAnimator.target = iv_icon

        btn_value.setOnClickListener {
            valueAnimator.start()
        }

        btn_object.setOnClickListener {
            objectAnimator.start()
        }
    }

}
