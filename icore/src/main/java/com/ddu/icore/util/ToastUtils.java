package com.ddu.icore.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.icore.R;
import com.ddu.icore.app.BaseApp;


public class ToastUtils {

    private static Toast sToast;

    public static void showSuccessToast(int resId) {
        showDefaultToast(resId, true);
    }

    public static void showSuccessToast(String message) {
        showDefaultToast(message, true);
    }

    public static void showErrorToast(int resId) {
        showDefaultToast(resId, false);
    }

    public static void showErrorToast(String message) {
        showDefaultToast(message, false);
    }

    public static void showImageToast(int imId) {
        showCustomToast("", imId, Toast.LENGTH_SHORT);
    }

    public static void showTextToast(int msgId) {
        showCustomToast(msgId, -1, Toast.LENGTH_SHORT);
    }

    public static void showTextToast(String msg) {
        showCustomToast(msg, -1, Toast.LENGTH_SHORT);
    }

    public static void showDefaultToast(String msg, boolean isSuccess) {
        int resId = isSuccess ? R.drawable.toast_right_icon : R.drawable.toast_error_icon;
        showCustomToast(msg, resId, Toast.LENGTH_SHORT);
    }

    public static void showDefaultToast(int resId, boolean isSuccess) {
        int imgResId = isSuccess ? R.drawable.toast_right_icon : R.drawable.toast_error_icon;
        showCustomToast(resId, imgResId, Toast.LENGTH_SHORT);
    }

    public static void showCustomToast(final String msg, final int imgRes, final int duration) {
        showToast(msg, imgRes, duration);
    }

    public static void showCustomToast(@StringRes int resId, final int imgRes, final int duration) {
        String msg = BaseApp.getContext().getString(resId);
        showToast(msg, imgRes, duration);
    }

    public static void showToast(final String msg, @DrawableRes final int imgResId, final int duration) {
        BaseApp.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sToast == null) {
                        sToast = new Toast(BaseApp.getContext());
                    }

                    LayoutInflater layoutInflater = LayoutInflater.from(BaseApp.getContext());
                    View toastRoot = layoutInflater.inflate(R.layout.toast_custom, null);

                    Space space = ViewUtils.findViewById(toastRoot, R.id.space_toast);
                    TextView tv = ViewUtils.findViewById(toastRoot, R.id.tv_toast);
                    ImageView iv = ViewUtils.findViewById(toastRoot, R.id.iv_toast);

                    if (TextUtils.isEmpty(msg)) {
                        space.setVisibility(View.GONE);
                    } else {
                        tv.setText(msg);
                        tv.setVisibility(View.VISIBLE);
                    }

                    if (imgResId == -1) {
                        space.setVisibility(View.GONE);
                    } else {
                        iv.setImageResource(imgResId);
                        iv.setVisibility(View.VISIBLE);
                    }
                    sToast.setView(toastRoot);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(duration);
                    sToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BaseApp.getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void cancel() {
        try {
            if (sToast != null) {
                sToast.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
