package com.ddu.icore.rx.bus;

/**
 * Created by yzbzz on 2018/2/9.
 */

public final class RxBusWith<T> extends RxBus {

    private final T action;

    private ActionCallBack<T> mActionCallBack;

    public RxBusWith(final T action) {
        this.action = action;
    }

    public ActionCallBack getActionCallBack() {
        return mActionCallBack;
    }

    public void setActionCallBack(ActionCallBack actionCallBack) {
        this.mActionCallBack = actionCallBack;
    }
}
