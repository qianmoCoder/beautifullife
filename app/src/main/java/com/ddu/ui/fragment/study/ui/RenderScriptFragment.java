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
import com.ddu.icore.util.sys.ViewUtils;
import com.ddu.util.DialogUtils;
import com.iannotation.Element;

/**
 * Created by lhz on 16/5/6.
 */
@Element("UI")
public class RenderScriptFragment extends DefaultFragment {

    private Button btnShow;

    private ImageView imageView;
    private LinearLayout linearLayout;
    private RenderScriptGaussianBlur blur;

    @NonNull
    public static RenderScriptFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(Companion.getARGUMENT_TASK_ID(), taskId);
        RenderScriptFragment fragment = new RenderScriptFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        blur = new RenderScriptGaussianBlur(getMContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_draw;
    }

    @Override
    public void initView() {
        imageView = ViewUtils.findViewById(getMView(), R.id.container);
        linearLayout = ViewUtils.findViewById(getMView(), R.id.layout);
        btnShow = ViewUtils.findViewById(getMView(), R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);

                linearLayout.setDrawingCacheEnabled(true);
                linearLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

                Bitmap bitmap = linearLayout.getDrawingCache();

                imageView.setImageBitmap(blur.gaussianBlur(25, bitmap));

                linearLayout.setVisibility(View.INVISIBLE);

                DialogUtils.showDialog(getMContext(), R.drawable.icon_add, "Hello", "OK", new DialogUtils.ITwoButtonListener() {
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
