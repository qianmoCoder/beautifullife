package com.ddu.icore.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;


public class DensityUtils {

    final static float DEFAULT_720P_HEIGHT = 1280.0f;

    public static int px2dip(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dip2px(@NonNull Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float getScale(@NonNull Activity act) {
        int height = act.getWindowManager().getDefaultDisplay().getHeight();
        return height / DEFAULT_720P_HEIGHT;
    }
}