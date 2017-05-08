package com.ddu.icore.common;


/**
 * Created by yzbzz on 2016/1/6.
 */
public interface IObserver<T> {

    void registerObserver();

    void onReceiverNotify(T godIntent);
}
