package com.ddu.ui.dialog;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddu.R;
import com.ddu.icore.dialog.BottomDialogFragment;

import java.lang.ref.WeakReference;

/**
 * Created by yzbzz on 2017/10/31.
 */

public class SelectDialog extends BottomDialogFragment {

    private WeakRefHandler weakRefHandler;

    public SelectDialog() {
        weakRefHandler = new WeakRefHandler(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
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
