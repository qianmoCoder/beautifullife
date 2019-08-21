package com.ddu.icore.dialog;

import android.app.Dialog;
import android.content.Context;
import com.ddu.icore.R;

/**
 * Created by yzbzz on 2019-08-19.
 */
public final class LoadingDialog {

    private Dialog mDialog = null;

    private LoadingDialog() {
    }

    public static LoadingDialog getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static LoadingDialog instance = new LoadingDialog();
    }


    public void show(Context context) {
        dismiss();
        if (null == mDialog) {
            mDialog = new Dialog(context, R.style.DialogTheme);
            mDialog.setContentView(R.layout.i_dialog_loading);
            mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(true);
        }
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
