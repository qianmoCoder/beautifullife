package com.ddu.ui.fragment

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.DialogFragment
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.databinding.FragmentMeBinding
import com.ddu.icore.callback.InConsumer3
import com.ddu.icore.common.ext.act
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.dialog.AlertDialogFragment
import com.ddu.icore.dialog.BottomDialogFragment
import com.ddu.icore.dialog.DefaultGridBottomDialogFragment
import com.ddu.icore.entity.BottomItem
import com.ddu.icore.entity.BottomItemEntity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.ui.help.ShapeInject
import com.ddu.ui.fragment.person.PhoneInfoFragment
import com.ddu.ui.fragment.person.SettingFragment
import com.ddu.util.NotificationUtils
import kotlinx.android.synthetic.main.fragment_me.*


/**
 * Created by yzbzz on 2018/1/17.
 */
class MeFragment : DefaultFragment() {

    var mHits = LongArray(COUNTS)
    var dialog: AlertDialogFragment? = null
    var nid = 0

    private lateinit var binding: FragmentMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMeBinding.inflate(layoutInflater)
    }

    override fun initData(savedInstanceState: Bundle?) {
        dialog = AlertDialogFragment().apply {
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

        binding.tvUsrName.text = "yzbzz"
        binding.tvUsrNumber.text = "186-xxxx-xxx"

        binding.oivBuddha.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#b2ffff00"))

        binding.oivFriendLink.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#b2fdbc40"))
        binding.oivFriendLink.setOnClickListener {
            showShareDialog()
        }

        binding.oivPlan.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#b200ff00"))

        binding.oivFav.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#ff0000"))

        binding.oivEggs.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#b2437fda"))
        binding.oivEggs.setOnClickListener {
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

        oiv_setting.setLefText(ShapeInject.TYPE_ROUND, Color.parseColor("#b234c749"))
        oiv_setting.setOnClickListener {
            startFragment(SettingFragment::class.java)
        }

        oiv_phone_info.enableDefaultLeftText(Color.parseColor("#b24897fa"))
        oiv_phone_info.setOnClickListener {
            startFragment(PhoneInfoFragment::class.java)
        }

        oiv_notification.enableDefaultLeftText(Color.parseColor("#b2ff4141"))
        oiv_notification.setOnClickListener {
            val intent = Intent("cn.android.intent.user.click")
            val pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationUtils.instance.getPrimaryNotification(ctx, "hello", "hello world")
            val builder1 = NotificationUtils.instance.getSecondNotification(ctx, "hello", "hello world")
            NotificationUtils.instance.notify(nid++, builder1)
//            NotificationUtils.instance.notify(2, builder1)
        }

        oiv_show_dialog.enableDefaultLeftText(Color.parseColor("#b234c749"))
        oiv_show_dialog.setOnClickListener {
            val dialog = AlertDialogFragment().apply {
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
            dialog.show(parentFragmentManager, "")
            BaseApp.postDelayed(Runnable {
                val sb = StringBuilder()
                sb.append("isadd: ${dialog.isAdded} ")
                sb.append("isDetached: ${dialog.isDetached} ")
                sb.append("isHidden: ${dialog.isHidden} ")
                sb.append("isRemoving: ${dialog.isRemoving} ")
                sb.append("isVisible: ${dialog.isVisible} ")
                dialog.dismissAllowingStateLoss()
            }, 2000)
        }

        oiv_show_bottom_dialog.enableDefaultLeftText(Color.parseColor("#b2ff00ff"))
        oiv_show_bottom_dialog.setOnClickListener {
            showBottomDialog()
        }


        rl_person_info.setOnClickListener {
            val i =  5 / 0
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

    private fun showBottomDialog() {
        var shareEntities = mutableListOf<BottomItem>()

        val github = BottomItem()
        github.id = 0
        github.icon = resources.getDrawable(R.drawable.me_friend_link_github)
        github.title = "GitHub"

        val blog = BottomItem()
        blog.id = 1
        blog.icon = resources.getDrawable(R.drawable.me_friend_link_blog)
        blog.title = "Blog"

        val blog1 = BottomItem()
        blog1.id = 1
        blog1.icon = resources.getDrawable(R.drawable.me_friend_link_blog)
        blog1.title = "Blog"

        val blog2 = BottomItem()
        blog2.id = 1
        blog2.icon = resources.getDrawable(R.drawable.me_friend_link_blog)
        blog2.title = "Blog"

        val blog3 = BottomItem()
        blog3.id = 1
        blog3.icon = resources.getDrawable(R.drawable.me_friend_link_blog)
        blog3.title = "Blog"

        shareEntities.add(github)
        shareEntities.add(blog)
        shareEntities.add(blog1)
        shareEntities.add(blog2)
        shareEntities.add(blog3)

        val dialogFragment = BottomDialogFragment.Builder()
                .setItems(shareEntities)
                .setTitle("分享")
                .gridLayout()
                .setItemClickListener(object : InConsumer3<DialogFragment, BottomItem, Int> {
                    override fun accept(a: DialogFragment, d: BottomItem, p: Int) {
                        a.dismissAllowingStateLoss()
                    }
                }).create()
        dialogFragment.showDialog(act)
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
                val dialog = AlertDialogFragment().apply {
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
                dialog.show(parentFragmentManager, "")

            }
        })
        shareDialog.show(parentFragmentManager, "")
    }
}