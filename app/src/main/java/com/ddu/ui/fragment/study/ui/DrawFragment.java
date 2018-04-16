package com.ddu.ui.fragment.study.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.help.ShapeInjectHelper;
import com.ddu.icore.ui.view.ShapeButton;
import com.ddu.icore.util.sys.ViewUtils;
import com.iannotation.ContentType;

/**
 * Created by lhz on 16/5/6.
 */
@ContentType("UI")
public class DrawFragment extends DefaultFragment {

    private ShapeButton shapeTextView;
    private LinearLayout linearLayout;

    @NonNull
    public static DrawFragment newInstance(String id) {
        Bundle arguments = new Bundle();
        DrawFragment fragment = new DrawFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shape_view;
    }

    @Override
    public void initView() {
        shapeTextView = ViewUtils.findViewById(getMView(), R.id.different_radius_test);
//        shapeTextView.setRadius(new float[]{0, 0, 20, 20, 40, 40, 60, 60});
        int height = shapeTextView.getHeight();
//        shapeTextView.getShapeInject().setSegmented(true);

        linearLayout = findViewById(R.id.ll_customer);
        final ShapeInjectHelper shapeInjectHelper = new ShapeInjectHelper(linearLayout);
        shapeInjectHelper.backgroundColor(Color.BLUE);
//        shapeInjectHelper.strokeWidth(2, Color.RED, 5, 5);
        shapeInjectHelper.shapeType(ShapeInjectHelper.ROUND_RECT);
        linearLayout.post(new Runnable() {
            @Override
            public void run() {

                shapeInjectHelper.setBackground();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
