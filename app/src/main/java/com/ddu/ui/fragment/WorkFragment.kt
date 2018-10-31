package com.ddu.ui.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.work.FragmentA
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * Created by yzbzz on 16/4/6.
 */
class WorkFragment : DefaultFragment() {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        oiv_fragment.setOnClickListener {
            //            getAppDetailSettingIntent(ctx)
//            BaseApp.postDelayed(Runnable {
//                val builder3 = NotificationUtils.instance.getNotification(ctx, "bbc", "abc", "abcdef", 1, NotificationUtils.PRIMARY_CHANNEL_ID)
//                NotificationUtils.instance.notify(2, builder3)
//            }, 1500)

//            val builder = Notification.Builder(context)
//                    .setTicker("Hello")
//                    .setContentTitle("world")
//                    .setContentText("bbc")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setWhen(System.currentTimeMillis())
//                    .setDefaults(Notification.DEFAULT_SOUND)
//                    .setLargeIcon(BitmapFactory.decodeResource(ctx.resources, R.mipmap.ic_launcher))
//                    .setAutoCancel(true)
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val channel = NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH)
//                builder.setChannelId("1")
//                mNManager.createNotificationChannel(channel)
//            }
//
//            val notification = builder.notification
//            mNManager.notify("123", ++count, notification)
            startFragment(FragmentA::class.java)
//            startActivity<ScrollingActivity2>()
        }
        setTitle(R.string.main_tab_work)
//        tv_money.text = DnsConfig.buildType
        //+ com.ddu.util.SystemUtils.getDeviceId()
//        tv_model.text = SystemUtils.getDeviceId(ctx) + " - " + SystemUtils.getMacAddress(ctx)
//        val isGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
//        if (!isGranted) {
//            ActivityCompat.requestPermissions(mActivity, arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
//        } else {
//            val context = BaseApp.mApp
//            val tm = context.telephonyManager
//            tv_model.text = "${tm.subscriberId}"
//            //            tv_model.setText(Build.MODEL + Build.BRAND + " " + Build.DEVICE + " " + Build.PRODUCT + Build.DISPLAY + Build.USER + "-" + Build.SERIAL + " " + SystemUtils.getDeviceId());
//        }

        val g = GradientDrawable()
        g.setColor(Color.BLUE)
        g.cornerRadius = 20f
        tv_money.background = g
        rv_item.setBackground("#4897fa")
    }

    companion object {

        fun newInstance(): WorkFragment {
            val fragment = WorkFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
