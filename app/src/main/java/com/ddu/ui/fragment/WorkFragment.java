package com.ddu.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ddu.R;
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

//                NotificationUtils.notification(mContext);

//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "18610909732"));
//                intent.putExtra("sms_body", "http://t.cn/RXlnf0d");
//                startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putInt("type", FragmentUtils.FRAGMENT_ADD);
                startFragment(WebFragment.class);
//                startActivity(new Intent(mContext, ShareActivity.class));
//                Intent intent = new Intent();
//                //http://t.cn/RXlnf0d
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("http://t.cn/RXlnf0d");
//                intent.setData(content_url);
//                startActivity(intent);
//                mActivity.overridePendingTransition(R.anim.bottom_view_anim_enter,R.anim.bottom_view_anim_exit);
//                LoginDialog loginDialog = LoginDialog.newInstance();
//                loginDialog.show(getFragmentManager(), "loginLog");

            }
        });
        setTitle(R.string.main_tab_work);
        mTvMoney.setText(DnsConfig.getBaseUrl());
        mTvModel.setText(Build.MODEL + Build.BRAND);

    }
}
