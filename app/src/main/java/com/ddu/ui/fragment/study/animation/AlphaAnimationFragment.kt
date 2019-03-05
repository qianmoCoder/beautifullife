package com.ddu.ui.fragment.study.animation

import android.view.animation.AnimationUtils
import com.ddu.R
import com.ddu.icore.common.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_animation_alpha.*

/**
 * Created by yzbzz on 16/4/8.
 */
@IElement("AN")
class AlphaAnimationFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_animation_alpha
    }

    override fun initView() {
        val alphaIn = AnimationUtils.loadAnimation(ctx, R.anim.alpha_in)
        val alphaOut = AnimationUtils.loadAnimation(ctx, R.anim.alpha_out)
        tv_alpha_in.setOnClickListener {
            tv_alpha_in.startAnimation(alphaIn)
        }
        tv_alpha_out.setOnClickListener {
            tv_alpha_out.startAnimation(alphaOut)
        }
    }

}
