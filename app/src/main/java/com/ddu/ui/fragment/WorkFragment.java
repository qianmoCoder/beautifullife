package com.ddu.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.R;
import com.ddu.app.App;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.view.OptionItemView;
import com.ddu.icore.util.DnsConfig;
import com.ddu.icore.util.sys.ViewUtils;

/**
 * Created by yzbzz on 16/4/6.
 */
public class WorkFragment extends DefaultFragment {

    private OptionItemView mOptionItemView;

    private TextView mTvMoney;
    private TextView mTvModel;

    @NonNull
    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    public void initView() {
        mTvMoney = findViewById(R.id.tv_money);
        mTvModel = findViewById(R.id.tv_model);
        mOptionItemView = ViewUtils.findViewById(mView, R.id.oiv_fragment);
        mOptionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(mContext, "hello", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.v("lhz", Log.getStackTraceString(e));
                        }
                    }
                }).start();
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
//                App.getMainHandler().postDelayed(new Runnable() {
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
//                App.getMainHandler().postDelayed(new Runnable() {
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
        });
        setTitle(R.string.main_tab_work);
        mTvMoney.setText(DnsConfig.getBuildType());
        //+ com.ddu.util.SystemUtils.getDeviceId()
        boolean isGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            Context context = App.getContext();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(tm.getSimOperatorName()+" ");
            stringBuilder.append(tm.getSubscriberId());
            mTvModel.setText(stringBuilder.toString());
//            mTvModel.setText(Build.MODEL + Build.BRAND + " " + Build.DEVICE + " " + Build.PRODUCT + Build.DISPLAY + Build.USER + "-" + Build.SERIAL + " " + SystemUtils.getDeviceId());
        }

    }
}
