package com.ddu.util;

import android.content.Context;
import android.support.v4.widget.Space;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.icore.util.ViewUtils;
import com.ddu.R;
import com.ddu.app.App;


public class ToastUtil {

    private static Toast sToast;

    private static Context mContext;

    static {
        mContext = App.getContext();
    }


    public static void showSuccessToast(int resId) {
        showSuccessToast(mContext.getString(resId));
    }

    public static void showSuccessToast(String message) {
        showDefaultToast(message, true);
    }

    public static void showErrorToast(int resId) {
        showErrorToast(mContext.getString(resId));
    }

    public static void showErrorToast(String message) {
        showDefaultToast(message, false);
    }

    public static void showImageToast(int imId) {
        showCustomToast(mContext, "", imId, Toast.LENGTH_SHORT);
    }

    public static void showTextToast(int msgId) {
        showTextToast(mContext.getString(msgId));
    }

    public static void showTextToast(String msg) {
        showCustomToast(mContext, msg, -1, Toast.LENGTH_SHORT);
    }

    private static void showDefaultToast(String msg, boolean isSuccess) {
        int resId = isSuccess ? R.drawable.toast_right_icon : R.drawable.toast_error_icon;
        showCustomToast(mContext, msg, resId, Toast.LENGTH_SHORT);
    }

    private static void showCustomToast(final Context context, final String msg, final int imgRes, final int duration) {
        App.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sToast == null) {
                        sToast = new Toast(context);
                    }

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
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

                    if (imgRes == -1) {
                        space.setVisibility(View.GONE);
                    } else {
                        iv.setImageResource(imgRes);
                        iv.setVisibility(View.VISIBLE);
                    }
                    sToast.setView(toastRoot);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    sToast.setDuration(duration);
                    sToast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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
