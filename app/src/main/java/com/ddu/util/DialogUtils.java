package com.ddu.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.support.annotation.NonNull;
import android.support.v4.widget.Space;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddu.icore.util.ViewUtils;
import com.ddu.R;


public class DialogUtils {

    @NonNull
    public static Dialog createLoadingDialog(@NonNull Context context) {

        Dialog loadingDialog = new Dialog(context, R.style.DDULoadingDialog);

        loadingDialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // 提示加载时，关闭Toast提示
                ToastUtil.cancel();
            }
        });

        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(R.layout.dialog_loading);
        return loadingDialog;
    }


    public static void showDialog(@NonNull Context context, int imgRes, String message, String btnTitle, final ISingleButtonListener listener) {
        final Dialog loadingDialog = createDefaultDialog(context, imgRes, message, "", btnTitle, true, listener);
        loadingDialog.show();
    }

    public static void showDialog(@NonNull Context context, int imgRes, String message, String btnCancelTitle, String btnOkTitle, final ITwoButtonListener listener) {
        final Dialog loadingDialog = createDefaultDialog(context, imgRes, message, btnCancelTitle, btnOkTitle, true, listener);
        loadingDialog.show();
    }

    @NonNull
    public static Dialog createDefaultDialog(@NonNull Context context, int ImgRes, String message, String btnCancelTitle, String btnOkTitle, boolean bCancelable,
                                             final ISingleButtonListener listener) {
        final Dialog dialog = new Dialog(context, R.style.DDULoadingDialog);
        dialog.setCancelable(bCancelable);
        dialog.setCanceledOnTouchOutside(bCancelable);

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_default, null);

        ImageView ivIcon = ViewUtils.findViewById(layout, R.id.iv_icon);
        ivIcon.setImageResource(ImgRes);

        TextView tx = ViewUtils.findViewById(layout, R.id.tv_message);
        tx.setText(message);

        Button btnCancel = ViewUtils.findViewById(layout, R.id.btn_left);
        btnCancel.setText(btnCancelTitle);

        Space space = ViewUtils.findViewById(layout, R.id.space);
        if (listener instanceof ITwoButtonListener) {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ITwoButtonListener) listener).onBtnCancelClickListener(dialog);
                }
            });
        } else {
            btnCancel.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }


        Button btnOk = (Button) layout.findViewById(R.id.btn_right);
        btnOk.setText(btnOkTitle);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClickListener(dialog);
            }
        });


        dialog.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // 提示加载时，关闭Toast提示
                ToastUtil.cancel();
            }
        });

        dialog.setContentView(layout);
        return dialog;
    }

    public interface ISingleButtonListener {
        void onBtnClickListener(Dialog dialog);
    }

    public interface ITwoButtonListener extends ISingleButtonListener {

        void onBtnCancelClickListener(Dialog dialog);
    }

}
