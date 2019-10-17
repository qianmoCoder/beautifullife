package com.ddu.icore.logic;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.IObserver;
import com.ddu.icore.common.ObserverManager;

/**
 * Created by yzbzz on 2019-10-12.
 */
abstract class BaseLogic implements IObserver {

    void registerObserver(String action) {
        ObserverManager.registerObserver(action, this);
    }

    void unRegisterObserver() {
        ObserverManager.unRegisterObserver(this);
    }

    void notifyListener(GodIntent godIntent) {
        ObserverManager.notify(godIntent);
    }
}
