package com.ddu.icore.logic;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.common.ObserverManager;

public class LogicManager {
    
    private LogicManager() {}
     
    public static LogicManager getInstance() {
        return SingletonHolder.instance;
    }
    
    private static class SingletonHolder {
        private static LogicManager instance = new LogicManager();
    }

    public void initAllLogic() {
//        GlobalLogic.getInstance().registerObserver();
    }

    public void releaseAllLogic() {
//        GlobalLogic.getInstance().unRegisterObserver();
    }

    public void notifyListener(GodIntent godIntent) {
        ObserverManager.notify(godIntent);
    }

}
