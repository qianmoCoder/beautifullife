package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.help.ShapeInject;
import com.iannotation.Element;

/**
 * Created by lhz on 16/5/6.
 */
@Element("UI")
public class DrawFragment extends DefaultFragment {

    @NonNull
    public static DrawFragment newInstance(String id) {
        Bundle arguments = new Bundle();
        DrawFragment fragment = new DrawFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shape_view;
    }

    @Override
    public void initView() {
        TextView tvOvalCode = findViewById(R.id.tv_oval_code);
        int color = mContext.getResources().getColor(R.color.c_ff4141);
        ShapeInject.inject(tvOvalCode).setShapeType(ShapeInject.TYPE_OVAL).setStroke(2, color).background();

        TextView tvRoundRectCode = findViewById(R.id.tv_round_rect_code);
        int roundRectColor = mContext.getResources().getColor(R.color.c_4897fa);
        ShapeInject.inject(tvRoundRectCode).setShapeType(ShapeInject.TYPE_ROUND_RECT).setStroke(2, roundRectColor).background();
    }

}
