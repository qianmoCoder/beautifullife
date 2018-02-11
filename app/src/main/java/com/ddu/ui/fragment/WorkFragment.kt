package com.ddu.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.ddu.R
import com.ddu.icore.app.BaseApp
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.DnsConfig
import kotlinx.android.synthetic.main.fragment_work.*
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

    override fun initView() {
        oiv_fragment.setOnClickListener {
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
        tv_money.text = DnsConfig.getBuildType()
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
