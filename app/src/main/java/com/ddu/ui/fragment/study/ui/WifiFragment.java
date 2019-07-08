package com.ddu.ui.fragment.study.ui;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2017/9/1.
 */
@IElement("UI")
public class WifiFragment extends DefaultFragment {

    private Button btnConnect;
    private Button btnDisConnect;

    private WifiManager mWifiManager;
    private WifiInfo wifiInfo;

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mWifiManager = (WifiManager) getMContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_study_wifi;
    }

    @Override
    public void initView() {
        btnConnect = findViewById(R.id.connect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mWifiManager.isWifiEnabled()) {
                    mWifiManager.setWifiEnabled(true);
                }
                mWifiManager.enableNetwork(wifiInfo.getNetworkId(),true);
            }
        });
        btnDisConnect = findViewById(R.id.disconnect);
        btnDisConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mWifiManager.disconnect();
                wifiInfo = mWifiManager.getConnectionInfo();
//                mWifiManager.disableNetwork(wifiInfo.getNetworkId());
                mWifiManager.setWifiEnabled(false);
            }
        });
    }

}
