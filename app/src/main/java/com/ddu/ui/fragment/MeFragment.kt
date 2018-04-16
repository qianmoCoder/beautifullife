package com.ddu.ui.fragment

import android.os.Bundle
import android.os.SystemClock
import com.ddu.R
import com.ddu.icore.dialog.DefaultDialogFragment
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_me.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class MeFragment : DefaultFragment() {

    var mHits = LongArray(COUNTS)
    var dialog: DefaultDialogFragment? = null

    override fun initData(savedInstanceState: Bundle?) {
        dialog = DefaultDialogFragment().apply {
            title = "彩蛋"
            msg = "逗你玩"
            leftText = "取消"
            rightText = "确定"
            mLeftClickListener = { _, _ ->
                dismissAllowingStateLoss()
            }
            mRightClickListener = { _, _ ->
                dismissAllowingStateLoss()
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView() {
        oiv_eggs.setOnClickListener {
            System.arraycopy(mHits, 1, mHits, 0, mHits.size - 1)
            mHits[mHits.size - 1] = SystemClock.uptimeMillis()

            if (mHits[0] >= SystemClock.uptimeMillis() - DURATION) {
                dialog?.let {
                    if (!it.isVisible) {
                        it.show(childFragmentManager, "")
                    }
                }
            }
        }

        setTitle(R.string.main_tab_me)
    }

    companion object {

        internal const val COUNTS = 5//点击次数
        internal const val DURATION = (3 * 1000).toLong()//规定有效时间

        fun newInstance(): MeFragment {
            val fragment = MeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}