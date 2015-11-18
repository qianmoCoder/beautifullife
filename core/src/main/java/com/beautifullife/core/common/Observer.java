package com.beautifullife.core.common;

/**
 * Created by admin on 2015/11/17.
 */
public interface Observer {

    void registerObserver();

    <T> void onReceiverNotify(int action, T object, int stateCode);
}
