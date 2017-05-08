package com.ddu.icore.refresh.internal;

public interface ILoadingLayout {

    void onPull(float scale);

    void refreshing();

    void releaseToRefresh();

    void reset();

    void pullToRefresh();

}
