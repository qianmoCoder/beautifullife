package com.ddu.ui.fragment.study.material;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.google.android.material.snackbar.Snackbar;
import com.iannotation.IElement;

import androidx.annotation.NonNull;

/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("MD")
public class SnackBarFragment extends DefaultFragment {

    private LinearLayout mLlSnackBar;
    private Button btnTop;
    private Button btnBottom;

    @NonNull
    public static SnackBarFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(Companion.getARGUMENT_TASK_ID(), taskId);
        SnackBarFragment fragment = new SnackBarFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_snackbar;
    }

    @Override
    public void initView() {
        mLlSnackBar = findViewById(R.id.ll_snack_bar);
        btnTop = findViewById(R.id.btn_top);
        btnBottom = findViewById(R.id.btn_bottom);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                snackbar.show();
            }
        });
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "hello", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
    }
}
