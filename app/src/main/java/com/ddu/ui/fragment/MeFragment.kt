package com.ddu.ui.fragment

import android.Manifest
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.callback.InConsumer3
import com.ddu.icore.common.ext.act
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.dialog.AlertDialogFragment
import com.ddu.icore.dialog.BottomDialogFragment
import com.ddu.icore.dialog.DefaultGridBottomDialogFragment
import com.ddu.icore.entity.BottomItem
import com.ddu.icore.entity.BottomItemEntity
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.person.PhoneInfoFragment
import com.ddu.ui.fragment.person.SettingFragment
import com.ddu.util.NotificationUtils
import kotlinx.android.synthetic.main.fragment_me.*
import java.lang.reflect.Field


/**
 * Created by yzbzz on 2018/1/17.
 */
class MeFragment : DefaultFragment() {

    private var mHits = LongArray(COUNTS)
    var dialog: AlertDialogFragment? = null
    private var nid = 0

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

        tv_usr_name.text = "yzbzz"
        tv_usr_number.text = "186-xxxx-xxx"

        oiv_buddha.enableDefaultLeftText(Color.parseColor("#b2ffff00"))

        oiv_friend_link.enableDefaultLeftText(Color.parseColor("#b2fdbc40"))
        oiv_friend_link.setOnClickListener {
            //            showShareDialog()
            val intent = Intent("cn.ddu.android.intent.NOTIFICATION_RECEIVED");
            intent.component = ComponentName("com.iflytek.mscv5plusdemo", "com.iflytek.broadcast.UserReceiver");
            mContext.sendBroadcast(intent)
        }


        oiv_plan.enableDefaultLeftText(Color.parseColor("#b200ff00"))

        oiv_fav.enableDefaultLeftText(Color.parseColor("#ff0000"))
        oiv_fav.setOnClickListener {
            //1、首先声明一个数组permissions，将所有需要申请的权限都放在里面
            val permissions = arrayOf<String>(Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET)

            ActivityCompat.requestPermissions(act, permissions, 100)
        }

        oiv_eggs.enableDefaultLeftText(Color.parseColor("#b2437fda"))
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

        oiv_setting.enableDefaultLeftText(Color.parseColor("#b234c749"))
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
//            val permissions = arrayOf<String>(Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.INTERNET)
//
//            ActivityCompat.requestPermissions(act, permissions, 100)
            setXiaoMiBadge(ctx,20)
        }
    }


    private fun setXiaoMiBadge(context: Context, number: Int) {
        try {
            val miuiNotificationClass = Class.forName("android.app.MiuiNotification")
            val miuiNotification = miuiNotificationClass.newInstance()
            val field: Field = miuiNotification.javaClass.getDeclaredField("messageCount")
            field.setAccessible(true)
            field.set(miuiNotification, if (number == 0) "" else number.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            // miui6之前
            val localIntent = Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE")
            localIntent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName().toString() + "/." + "MainActivity")
            localIntent.putExtra("android.intent.extra.update_application_message_text", if (number == 0) "" else number.toString())
            context.sendBroadcast(localIntent)
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
        val shareEntities = mutableListOf<BottomItemEntity>()

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