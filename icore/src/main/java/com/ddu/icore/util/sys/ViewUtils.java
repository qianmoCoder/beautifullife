package com.ddu.icore.util.sys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by yzbzz on 16/3/1.
 */
public class ViewUtils {

    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

    @NonNull
    public static <T extends View> T findViewById(@NonNull View view, int resId) {
        return (T) view.findViewById(resId);
    }

    @NonNull
    public static <T extends View> T findViewById(@NonNull Activity activity, int resId) {
        return (T) activity.findViewById(resId);
    }


    @NonNull
    public static Rect getLocationInView(@Nullable View parent, @Nullable View child) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("parent and child can not be null .");
        }

        View decorView = null;
        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }

        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;

        if (child == parent) {
            child.getHitRect(result);
            return result;
        }
        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!tmp.getClass().equals(FRAGMENT_CON)) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
        }
        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }

}
