package com.ddu.ui.dialog;

import android.os.Message;

import com.ddu.R;
import com.ddu.icore.dialog.AbsBottomDialogFragment;

import java.lang.ref.WeakReference;

/**
 * Created by yzbzz on 2017/10/31.
 */

public class SelectDialog extends AbsBottomDialogFragment {

    private WeakRefHandler weakRefHandler;

    public SelectDialog() {
        weakRefHandler = new WeakRefHandler(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    private static class WeakRefHandler extends android.os.Handler {
        WeakReference<SelectDialog> mActivity;

        public WeakRefHandler(SelectDialog selectDialog) {
            mActivity = new WeakReference<>(selectDialog);
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (mActivity.get() == null) {
                return;
            }
            switch (what) {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    private void ordersQueryPayStatus() {

    }
}
