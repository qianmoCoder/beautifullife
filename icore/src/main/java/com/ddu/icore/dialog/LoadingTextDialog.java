package com.ddu.icore.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ddu.icore.R;

public final class LoadingTextDialog {

    private Dialog dialog = null;

    private LoadingTextDialog() {
    }

    private static class Holder {
        private static LoadingTextDialog instance = new LoadingTextDialog();
    }

    public static LoadingTextDialog getInstance() {
        return Holder.instance;
    }

    public void show(Activity activity, String showText) {
        dismiss();
        dialog = new Dialog(activity, R.style.DialogTheme);
        dialog.setContentView(R.layout.i_dialog_loading_text);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(showText)) {
            TextView text = dialog.findViewById(R.id.text);
            text.setText(showText);
        }
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                activity.onKeyUp(keyCode, keyEvent);
            }
            return true;
        });
        dialog.show();
    }

    public void show(Activity activity) {
        show(activity, null);
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
