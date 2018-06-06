package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.effect.CurveFloatingPathEffect
import com.ddu.ui.effect.CurvePathFloatingAnimator
import com.ddu.ui.effect.ScaleFloatingAnimator
import com.ddu.ui.view.FloatingText
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui_textview.*

/**
 * Created by yzbzz on 16/4/14.
 */
@Element("UI")
class TextViewFragment : DefaultFragment(), View.OnClickListener {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_textview
    }

    override fun initView() {
        tv_reveal_text_view.setOnClickListener(this)
        tv_translateView.setOnClickListener(this)
        tv_curveView.setOnClickListener(this)
        tv_scaleView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_reveal_text_view -> tv_reveal_text_view.replayAnimation()
            R.id.tv_translateView -> showFloatingTextByTranslate(view)
            R.id.tv_curveView -> showFloatingTextByCurve(view)
            R.id.tv_scaleView -> showFloatingTextByScale(view)
            else -> {
            }
        }
    }

    private fun showFloatingTextByTranslate(view: View) {
        val translateFloatingText = FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.RED)
                .textSize(100)
                .textContent("+1000")
                .build()
        translateFloatingText.attach2Window()
        translateFloatingText.startFloating(view)
    }

    private fun showFloatingTextByCurve(view: View) {
        val cubicFloatingText = FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.RED)
                .textSize(100)
                .floatingAnimatorEffect(CurvePathFloatingAnimator())
                .floatingPathEffect(CurveFloatingPathEffect())
                .textContent("Hello! ").build()
        cubicFloatingText.attach2Window()
        cubicFloatingText.startFloating(view)
    }

    private fun showFloatingTextByScale(view: View) {
        val scaleFloatingText = FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.parseColor("#7ED321"))
                .textSize(100)
                .offsetY(-100)
                .floatingAnimatorEffect(ScaleFloatingAnimator())
                .textContent("+188").build()
        scaleFloatingText.attach2Window()
        scaleFloatingText.startFloating(view)
    }

    companion object {

        fun newInstance(taskId: String): TextViewFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = TextViewFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
