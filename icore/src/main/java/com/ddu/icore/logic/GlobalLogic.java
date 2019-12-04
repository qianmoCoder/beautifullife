package com.ddu.icore.logic;


import com.ddu.icore.aidl.GodIntent;

public class GlobalLogic extends BaseLogic {

    private GlobalLogic() {
    }

    public static GlobalLogic getInstance() {
        return SingletonHolder.instance;
    }

    public void registerObserver() {
        registerObserver(Actions.CLIENT_MSG_ACTION);
    }

    @Override
    public void onReceiverNotify(GodIntent godIntent) {
        notifyListener(godIntent);
    }

    private static class SingletonHolder {
        private static GlobalLogic instance = new GlobalLogic();
    }
}
