package com.ddu.ui.fragment.study.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.RenderScriptGaussianBlur;
import com.ddu.icore.util.ViewUtils;
import com.ddu.util.DialogUtils;

/**
 * Created by lhz on 16/5/6.
 */
public class RenderScriptFragment extends DefaultFragment {

    private Button btnShow;

    private ImageView imageView;
    private LinearLayout linearLayout;
    private RenderScriptGaussianBlur blur;

    @NonNull
    public static RenderScriptFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        RenderScriptFragment fragment = new RenderScriptFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        blur = new RenderScriptGaussianBlur(mContext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_draw;
    }

    @Override
    public void initView() {
        imageView = ViewUtils.findViewById(mView, R.id.container);
        linearLayout = ViewUtils.findViewById(mView, R.id.layout);
        btnShow = ViewUtils.findViewById(mView, R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);

                linearLayout.setDrawingCacheEnabled(true);
                linearLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

                Bitmap bitmap = linearLayout.getDrawingCache();

                imageView.setImageBitmap(blur.gaussianBlur(25, bitmap));

                linearLayout.setVisibility(View.INVISIBLE);

                DialogUtils.showDialog(mContext, R.drawable.toast_right_icon, "Hello", "OK", new DialogUtils.ITwoButtonListener() {
                    @Override
                    public void onBtnCancelClickListener(@NonNull Dialog dialog) {
                        dialog.dismiss();
                        imageView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onBtnClickListener(@NonNull Dialog dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
