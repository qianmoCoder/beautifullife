package com.ddu.ui.fragment.study.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.dialog.BottomSheetDialog;

/**
 * Created by yzbzz on 2017/5/25.
 */

public class DialogFragment extends DefaultFragment {

    private Button btnUI;
    private Button btnBottomDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_dialog;
    }

    @Override
    public void initView() {
        btnUI = findViewById(R.id.btn_dialog);
        btnBottomDialog = findViewById(R.id.btn_bottom_dialog);
        btnBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("我是标题");
                builder.setMessage("当文字eiwrowejowoejjwoidosjsoie");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.newInstance();
                        bottomSheetDialog.show(getFragmentManager(),"bottomSheetDialog");
                    }
                });
                builder.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
