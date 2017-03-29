package com.ddu.logic.common;


import com.ddu.icore.aidl.GodIntent;

/**
 * Created by yzbzz on 2016/1/6.
 */
public interface UIObserver {

    void registerObserver();

    void onReceiverNotify(GodIntent godIntent);
}
