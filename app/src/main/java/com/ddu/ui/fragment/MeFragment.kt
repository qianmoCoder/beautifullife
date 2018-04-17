package com.ddu.ui.fragment

import android.os.Bundle
import android.os.SystemClock
import com.ddu.R
import com.ddu.icore.dialog.DefaultDialogFragment
import com.ddu.icore.dialog.DefaultGridBottomDialogFragment
import com.ddu.icore.entity.BottomItemEntity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.person.SettingFragment
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
        setTitle(R.string.main_tab_me)

        tv_usr_name.text = "yzbzz"
        tv_usr_number.text = "186-xxxx-xxx"

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
        oiv_setting.setOnClickListener {
            startFragment(SettingFragment::class.java)
        }

        oiv_friend_link.setOnClickListener {
            showShareDialog()
        }
    }

    companion object {

        internal const val COUNTS = 10//点击次数
        internal const val DURATION = (3 * 1000).toLong()//规定有效时间

        fun newInstance(): MeFragment {
            val fragment = MeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun showShareDialog() {
        var shareEntities = mutableListOf<BottomItemEntity>()

        val github = BottomItemEntity()
        github.name = "GitHub"
        github.resId = R.drawable.me_friend_link_github
        github.data = "https://github.com/yzbzz"

        val blog = BottomItemEntity()
        blog.name = "Blog"
        blog.resId = R.drawable.me_friend_link_blog
        blog.data = "http://yzbzz.github.io"

        shareEntities.add(github)
        shareEntities.add(blog)

        val shareDialog = DefaultGridBottomDialogFragment.newInstance(list = shareEntities, cb = { data, _, shareDialog ->
            data?.apply {
                shareDialog.dismissAllowingStateLoss()
                val dialog = DefaultDialogFragment().apply {
                    title = "即将前往"
                    msg = "${data.data}"
                    leftText = "取消"
                    rightText = "确定"
                    mLeftClickListener = { _, _ ->
                        dismissAllowingStateLoss()
                    }
                    mRightClickListener = { _, _ ->
                        dismissAllowingStateLoss()
                        val args = Bundle()
                        args.putString("title", name)
                        args.putString("url", data.data)
                        startFragment(WebFragment::class.java, args)
                    }
                }
                dialog.show(fragmentManager, "")

            }
        })
        shareDialog.show(fragmentManager, "")
    }
}