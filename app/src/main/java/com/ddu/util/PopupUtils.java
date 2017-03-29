package com.ddu.util;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ddu.R;


/**
 * Created by yzbzz on 16/4/18.
 */
public class PopupUtils {

    public static void showDefaultPopupWindow(@NonNull Activity activity, @NonNull View view, View popupView) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        PopupWindow popupWindow = showPopupWindow(popupView);
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 0, location[1] + frame.top - popupWindow.getHeight());
        popupWindow.update();

    }

    @NonNull
    public static PopupWindow showPopupWindow(View popupView) {
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.MorePopupAnim);
        return popupWindow;
    }
}
