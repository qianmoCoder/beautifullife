package com.ddu.ui.fragment.study.customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.activity.phrase.CustomSeparatorActivity
import com.ddu.ui.activity.phrase.MultiSeparatorActivity
import com.ddu.ui.activity.phrase.OneSeparatorActivity
import com.ddu.ui.activity.phrase.TwoSeparatorActivity
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_style_phrase.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("customer")
class StylePhraseFragment : DefaultFragment(), View.OnClickListener {


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_style_phrase
    }

    override fun initView() {
        btn_one.setOnClickListener(this)
        btn_two.setOnClickListener(this)
        btn_multi.setOnClickListener(this)
        btn_custom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_one -> {
                startActivity<OneSeparatorActivity>()
            }
            R.id.btn_two -> {
                startActivity(Intent(ctx, TwoSeparatorActivity::class.java))
            }
            R.id.btn_multi -> {
                startActivity(Intent(ctx, MultiSeparatorActivity::class.java))
            }
            R.id.btn_custom -> {
                startActivity(Intent(ctx, CustomSeparatorActivity::class.java))
            }
        }
    }

    companion object {

        fun newInstance(taskId: String): StylePhraseFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = StylePhraseFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
