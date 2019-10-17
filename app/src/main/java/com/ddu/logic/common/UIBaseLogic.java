package com.ddu.logic.common;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.ObserverManager;

public abstract class UIBaseLogic {

    public UIBaseLogic() {
    }

    public void notifyUi(GodIntent godIntent) {
        ObserverManager.notify(godIntent);
    }

    public abstract void initActionListener();

    public void onReceiveAction(GodIntent msg) {

    }
}
