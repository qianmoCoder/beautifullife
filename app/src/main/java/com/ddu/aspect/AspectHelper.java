package com.ddu.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;

/**
 * Created by yzbzz on 2018/1/10.
 */

public class AspectHelper {

    public static final String TAG = "AspectHelper";

    public static void sendEventForData(JoinPoint joinPoint) {
        Log.v(TAG, joinPoint.toLongString());
    }
}
