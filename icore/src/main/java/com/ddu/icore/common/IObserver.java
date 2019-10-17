package com.ddu.icore.common;


import com.ddu.icore.aidl.GodIntent;

/**
 * Created by yzbzz on 2016/1/6.
 */
public interface IObserver {

    void onReceiverNotify(GodIntent t);
}
