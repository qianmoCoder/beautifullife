package com.ddu.ui.fragment.study.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.StylePhrase
import com.iannotation.Element
import kotlinx.android.synthetic.main.fragment_ui_textphrase.*

/**
 * Created by yzbzz on 16/4/14.
 */
@Element("UI")
class TextPhraseFragment : DefaultFragment() {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_textphrase
    }

    override fun initView() {
        val oneString = getString(R.string.text_phrase_one)
        val twoString = getString(R.string.text_phrase_two)
        val threeString = getString(R.string.text_phrase_three)

        tv_one_hint.text = "原文：$oneString"
        tv_two_hint.text = "原文：$twoString"
        tv_three_hint.text = "原文：$threeString"

        val oneText = StylePhrase(oneString)
                .setInnerFirstColor(Color.BLUE)
                .setInnerFirstSize(20)
                .format()
        tv_one.text = oneText

        val twoText = StylePhrase(twoString)
                .setInnerFirstColor(Color.BLUE)
                .setInnerFirstSize(20)
                .setInnerSecondColor(Color.GREEN)
                .setInnerSecondSize(15)
                .format()
        tv_two.text = twoText

        val builder = StylePhrase.Builder()
        builder.separator = "()"
        builder.setColor(Color.GREEN)
        builder.setSize(15)
//        builder.addParcelableSpan(object : URLSpan("http://www.baidu.com"){
//            override fun onClick(widget: View?) {
//                super.onClick(widget)
//                Toast.makeText(ctx,"点我",Toast.LENGTH_SHORT).show()
//            }
//        })
        builder.addParcelableSpan(StrikethroughSpan())
        builder.addParcelableSpan(StyleSpan(Typeface.BOLD_ITALIC))
        val threeText = StylePhrase(threeString)
                .setInnerFirstColor(Color.BLUE)
                .setInnerFirstSize(20)
                .setInnerSecondColor(Color.RED)
                .setInnerSecondSize(25)
                .addBuilder(builder)
                .format()
//        tv_three.movementMethod = LinkMovementMethod()
        tv_three.text = threeText
    }

    companion object {

        fun newInstance(taskId: String): TextPhraseFragment {
            val arguments = Bundle()
            arguments.putString(DefaultFragment.ARGUMENT_TASK_ID, taskId)
            val fragment = TextPhraseFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
