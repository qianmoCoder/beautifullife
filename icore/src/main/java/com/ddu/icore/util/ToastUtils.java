package com.ddu.icore.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ddu.icore.R;
import com.ddu.icore.app.BaseApp;
import com.ddu.icore.util.sys.ViewUtils;


public class ToastUtils {

    private Toast mToast;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View mView;
    private TextView mTextView;

    private ToastUtils() {
        mContext = BaseApp.Companion.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        initToast();
        initView();
    }

    private void initToast() {
        mToast = new Toast(mContext);
        mToast.setGravity(Gravity.CENTER, 0, 0);
    }


    private void initView() {
        mView = mLayoutInflater.inflate(R.layout.default_toast, null);
        mTextView = ViewUtils.findViewById(mView, R.id.tv_toast);
    }


    private static class SingletonHolder {
        private static ToastUtils instance = new ToastUtils();
    }

    public static ToastUtils getInstance() {
        return ToastUtils.SingletonHolder.instance;
    }


    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    public static <T> void showToast(final T text, final int duration) {
        BaseApp.Companion.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (text instanceof String) {
                        getInstance().showTextToast((String) text, duration);
                    } else if (text instanceof Integer) {
                        getInstance().showTextToast((Integer) text, duration);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (text instanceof String) {
                        Toast.makeText(BaseApp.Companion.getContext(), (String) text, duration).show();
                    } else if (text instanceof Integer) {
                        Toast.makeText(BaseApp.Companion.getContext(), (Integer) text, duration).show();
                    }

                }
            }
        });
    }

    private void showTextToast(@StringRes int resId, int duration) {
        mTextView.setText(resId);
        mToast.setDuration(duration);
        mToast.show();
    }

    private void showTextToast(CharSequence text, int duration) {
        mTextView.setText(text);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void cancel() {
        try {
            getInstance().mToast.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
