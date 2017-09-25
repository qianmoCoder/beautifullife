package com.ddu.logic.common;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.ObserverManager;

public abstract class UIBaseLogic {

    private ObserverManager observerManager;

    public UIBaseLogic() {
        observerManager = ObserverManager.getInstance();
    }

    public void notifyUi(GodIntent godIntent) {
        observerManager.notify(godIntent);
    }

    public abstract void initActionListener();

    public void onReceiveAction(GodIntent msg) {

    }
}
