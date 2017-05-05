package com.ddu.icore.refresh;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yzbzz on 2017/5/5.
 */

public class PullToRefreshHelper {

    public static final int DISABLED = 0x0;
    public static final int PULL_FROM_START = 0x1;
    public static final int PULL_FROM_END = 0x2;
    public static final int BOTH = 0x3;
    public static final int MANUAL_REFRESH_ONLY = 0x4;

    @IntDef({DISABLED, PULL_FROM_START, PULL_FROM_END, BOTH, MANUAL_REFRESH_ONLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {

    }
}
