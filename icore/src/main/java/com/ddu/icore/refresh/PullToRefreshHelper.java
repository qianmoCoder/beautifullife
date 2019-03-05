package com.ddu.icore.refresh;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yzbzz on 2017/5/5.
 */

public class PullToRefreshHelper {

    public static final int DISABLED = 0x0;
    public static final int PULL_FROM_START = 0x1;
    public static final int PULL_FROM_END = 0x2;
    public static final int BOTH = 0x3;
    public static final int MANUAL_REFRESH_ONLY = 0x4;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({DISABLED, PULL_FROM_START, PULL_FROM_END, BOTH, MANUAL_REFRESH_ONLY})
    public @interface Mode {

    }

    public void setMode(@Mode int mode) {

    }

    public void test() {
        PullToRefreshHelper pullToRefreshHelper = new PullToRefreshHelper();
        pullToRefreshHelper.setMode(PULL_FROM_START);
    }
 }
