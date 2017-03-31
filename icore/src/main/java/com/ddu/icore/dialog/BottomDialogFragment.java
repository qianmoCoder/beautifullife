
package com.ddu.icore.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ddu.icore.R;


public class BottomDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), getTheme());

        Dialog dialog = builder.create();
        dialog.show();

        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.windowAnimations = R.style.BottomDialogAnim;
        window.setAttributes(wlp);

        return dialog;
    }

}
