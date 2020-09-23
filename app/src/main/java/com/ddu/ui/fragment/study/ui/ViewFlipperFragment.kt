package com.ddu.ui.fragment.study.ui

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_view_flipper.*

/**
 * Created by yzbzz on 2017/11/14.
 */
@IElement(value = "UI")
class ViewFlipperFragment : DefaultFragment(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private val SCROLL_ANIM_DURATION = 10000f

    private var titleList = arrayListOf<String>()

    private var mScrollAnimator: ValueAnimator? = null

    private var mScrolling = false

    override fun getLayoutId(): Int {
        return R.layout.fragment_view_flipper
    }

    override fun initView() {
        setDefaultTitle("ViewFlipperFragment")

        vf_items.inAnimation = AnimationUtils.loadAnimation(ctx, R.anim.anim_in)
        vf_items.outAnimation = AnimationUtils.loadAnimation(ctx, R.anim.anim_out)

        for (index in 1..10) {
            val ll = layoutInflater.inflate(R.layout.fragment_recycle_view_vp_item, null) as LinearLayout
            ll.findViewById<TextView>(R.id.tv_name).text = "小班:$index"
            vf_items.addView(ll)
        }
        vf_items.flipInterval = 200

        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)

        titleList.add("你是天上最受宠的一架钢琴")
        titleList.add("我是丑人脸上的鼻涕")
        titleList.add("你发出完美的声音")
        titleList.add("我被默默揩去")
        titleList.add("你冷酷外表下藏着诗情画意")
        titleList.add("我已经够胖还吃东西")
        titleList.add("你踏着七彩祥云离去")
        titleList.add("我被留在这里")

        vt_item.setTextList(titleList)
        vt_item.setText(26f, 5, Color.RED)
        vt_item.setTextStillTime(200)
        vt_item.setAnimTime(100)
        vt_item.setOnItemClickListener {

        }

        creditsroll.setOnClickListener {
            if (!mScrolling) {
                animateScroll()
            } else {
                stopScrollAnimation()
            }
        }

        seekbar.setOnSeekBarChangeListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {}
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start -> {
//                vf_items.startFlipping()
                vt_item.startAutoScroll()

                rtv.playNumber(1000)
            }
            R.id.btn_stop -> {
//                vf_items.stopFlipping()
                vt_item.stopAutoScroll()
            }
        }
    }

    private fun animateScroll() {
        mScrolling = true
        mScrollAnimator = ObjectAnimator.ofInt(seekbar, "progress", seekbar.getProgress(), seekbar.getMax())
        mScrollAnimator?.setDuration(
                (SCROLL_ANIM_DURATION * (1 - seekbar.progress.toFloat() / seekbar.max)).toLong())
        mScrollAnimator?.setInterpolator(LinearInterpolator())
        mScrollAnimator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mScrolling = false
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })

        mScrollAnimator?.start()
    }

    private fun stopScrollAnimation() {
        if (mScrollAnimator != null) {
            mScrollAnimator?.cancel()
            mScrollAnimator = null
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        creditsroll.setScrollPosition(progress / 100000f)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        if (mScrolling) {
            stopScrollAnimation()
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        TODO("Not yet implemented")
    }
}