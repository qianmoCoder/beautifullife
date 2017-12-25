package com.ddu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by yzbzz on 2017/12/19.
 */

public class NetInfoBroadcastReceiver extends BroadcastReceiver {

    private long time = System.currentTimeMillis();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equalsIgnoreCase(action)) {
            int mWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            if (mWifiState == WifiManager.WIFI_STATE_ENABLED) {
                long newTime = System.currentTimeMillis();
                Log.v("lhz", " enabled time: " + (newTime - time));
                time = newTime;
            } else if (mWifiState == WifiManager.WIFI_STATE_DISABLED) {
                long newTime = System.currentTimeMillis();
                Log.v("lhz", " disabled time: " + (newTime - time));
                time = newTime;
            }
        }
    }
}
