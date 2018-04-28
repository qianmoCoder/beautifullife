package com.ddu.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.ddu.R
import com.ddu.icore.app.BaseApp
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.util.NotificationUtils
import kotlinx.android.synthetic.main.fragment_work.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.telephonyManager

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
            val builder = NotificationUtils.instance.getNotification(ctx, "hello", "world", "hello world", 1, NotificationUtils.PRIMARY_CHANNEL_ID)
            val builder1 = NotificationUtils.instance.getNotification(ctx, "hello", "world", "hello world", 1, NotificationUtils.PRIMARY_CHANNEL_SECOND_ID)
            NotificationUtils.instance.notify(1, builder)
            NotificationUtils.instance.notify(2, builder1)
            BaseApp.postDelayed(Runnable {
                val builder3 = NotificationUtils.instance.getNotification(ctx, "bbc", "abc", "abcdef", 1, NotificationUtils.PRIMARY_CHANNEL_ID)
                NotificationUtils.instance.notify(2, builder3)
            }, 1500)

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
//            startFragment(FragmentA::class.java)
        }
        setTitle(R.string.main_tab_work)
//        tv_money.text = DnsConfig.buildType
        //+ com.ddu.util.SystemUtils.getDeviceId()
        val isGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(mActivity, arrayOf(Manifest.permission.READ_PHONE_STATE), 0)
        } else {
            val context = BaseApp.mApp
            val tm = context.telephonyManager
            tv_model.text = "${tm.subscriberId}"
            //            tv_model.setText(Build.MODEL + Build.BRAND + " " + Build.DEVICE + " " + Build.PRODUCT + Build.DISPLAY + Build.USER + "-" + Build.SERIAL + " " + SystemUtils.getDeviceId());
        }

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
