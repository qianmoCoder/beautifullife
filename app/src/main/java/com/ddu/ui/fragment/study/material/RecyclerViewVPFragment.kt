package com.ddu.ui.fragment.study.material

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ddu.R
import com.ddu.icore.ui.adapter.common.ViewHolder
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_animator.*
import kotlinx.android.synthetic.main.fragment_recycle_view_vp.*


/**
 * Created by yzbzz on 2017/3/31.
 */
@IElement("MD")
class RecyclerViewVPFragment : DefaultFragment() {

    private lateinit var mAdapter: AutoPollAdapter
    private var scrollBy = 0

    override fun initData(savedInstanceState: Bundle?) {}
    override fun getLayoutId(): Int {
        return R.layout.fragment_recycle_view_vp
    }

    override fun initView() {
        scrollBy = resources.getDimension(R.dimen.dp_50).toInt()

        val items = arrayListOf<String>()

        for (index in 0..100) {
            items.add("向日葵:${index}班")
        }

        mAdapter = AutoPollAdapter(items)
        rv_vp.adapter = mAdapter
        rv_vp.layoutManager = LinearLayoutManager(mContext)

        (rv_vp.layoutManager as LinearLayoutManager).scrollToPosition(Integer.MAX_VALUE / 2)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_vp)

        btn_start.setOnClickListener {
            showAnimator(rv_vp)
        }
    }

    private fun showAnimator(vp: RecyclerView) {
        val animator1 = ObjectAnimator.ofFloat(vp, View.TRANSLATION_Y, 30f, 0f)
        animator1.duration = 300
        animator1.interpolator = AnticipateOvershootInterpolator()
        animator1.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                showValueAnimator(vp)
            }
        })
        animator1.start()

    }

    private fun showValueAnimator(vp: RecyclerView) {
        val mValueAnimator = ValueAnimator.ofInt(500)
        mValueAnimator.duration = 3000
        mValueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                vp.visibility = View.GONE
                tv_class_name.visibility = View.VISIBLE
            }
        })
        mValueAnimator.addUpdateListener {
            vp.scrollBy(80, 80)
//            vp.scrollBy(scrollBy * 2, scrollBy * 2)
        }
        mValueAnimator.start()
    }

    class AutoPollAdapter(private val mData: List<String>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recycle_view_vp_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                setText(R.id.tv_name, mData[position % mData.size])
            }
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE
        }
    }

}