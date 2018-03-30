package com.ddu.ui.fragment

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.ddu.R
import com.ddu.icore.app.BaseApp
import com.ddu.icore.ui.fragment.DefaultFragment
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
        var count = 1
        val mNManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        oiv_fragment.setOnClickListener {
//            NotificationUtils.sendNotification(ctx, "hello", "world", "123", 1, null)

            val builder = Notification.Builder(context)
                    .setTicker("Hello")
                    .setContentTitle("world")
                    .setContentText("bbc")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setLargeIcon(BitmapFactory.decodeResource(ctx.resources, R.mipmap.ic_launcher))
                    .setAutoCancel(true)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH)
                builder.setChannelId("1")
                mNManager.createNotificationChannel(channel)
            }

            val notification = builder.notification
            mNManager.notify("123", ++count, notification)
//            Navigator.startShareFragmentDialog(act as FragmentActivity)
//            d.show(fragmentManager, "")
//            startFragment(FragmentA::class.java)
            //                Log.v("lhz", "v: " + v.getX() + " " + v.getX());
            //                Log.v("lhz", "v p: " + v.getPivotX() + " " + v.getPivotY());
            //                Log.v("lhz", "v r: " + v.getRotationX() + " " + v.getRotationY());
            //                Log.v("lhz", "v s: " + v.getScaleX() + " " + v.getScaleY());
            //                Log.v("lhz", "v sr: " + v.getScrollX() + " " + v.getScrollY());
            //                Log.v("lhz", "v t: " + v.getTranslationX() + " " + v.getTranslationY());
            //                TouchDelegate touchDelegate = mOptionItemView.getTouchDelegate();
            //                NotificationUtils.notification(mContext);

            //                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "18610909732"));
            //                intent.putExtra("sms_body", "http://t.cn/RXlnf0d");
            //                startActivity(intent);
            //                Bundle bundle = new Bundle();
            //                bundle.putInt("type", FragmentUtils.FRAGMENT_ADD);
            //                startFragment(WebFragment.class);
            //                startActivity(new Intent(mContext, WebActivity.class));
            //                Intent intent = new Intent();
            //                //http://t.cn/RXlnf0d
            //                intent.setAction("android.intent.action.VIEW");
            //                Uri content_url = Uri.parse("http://t.cn/RXlnf0d");
            //                intent.setData(content_url);
            //                startActivity(intent);
            //                mActivity.overridePendingTransition(R.anim.bottom_view_anim_enter,R.anim.bottom_view_anim_exit);
            //                final WaitingDialog selectDialog = new WaitingDialog();
            //                selectDialog.show(getFragmentManager(), "");
            //                BaseApp.Companion().postDelayed(new Runnable() {
            //                    @Override
            //                    public void run() {
            //                        Log.v("lhz", "isDetached: " + selectDialog.isDetached());
            //                        Log.v("lhz", "isHidden: " + selectDialog.isHidden());
            //                        Log.v("lhz", "isRemoving: " + selectDialog.isRemoving());
            //                        Log.v("lhz", "isVisible: " + selectDialog.isVisible());
            //                        Log.v("lhz", "isAdded: " + selectDialog.isAdded());
            //                        Log.v("lhz", "isInLayout: " + selectDialog.isInLayout());
            //                    }
            //                }, 2000);

            //                startFragment(WebFragment.class);
            //                BaseApp.Companion().postDelayed(new Runnable() {
            //                    @Override
            //                    public void run() {
            ////                        Fragment fragment = getFragmentManager().getPrimaryNavigationFragment();
            ////                        Log.v("lhz","ff: " + fragment.getClass().getName());
            ////                        List<Fragment> fragments = getFragmentManager().getFragments();
            ////                        for (Fragment f : fragments) {
            ////                            if (f instanceof DialogFragment) {
            ////                                Log.v("lhz", "name: " + f.getClass().getName());
            ////                            }
            ////                        }
            //                        LoginDialog loginDialog = LoginDialog.newInstance();
            ////                        loginDialog.setTag("new tag");
            ////                        loginDialog.show(getFragmentManager(), "loginLog");
            //
            //                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
            //                        transaction.add(loginDialog, "loading");
            //                        transaction.commitAllowingStateLoss();
            //
            //                    }
            //                }, 1500);
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
