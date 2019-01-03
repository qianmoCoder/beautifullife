package com.ddu.ui.fragment.study.customer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.help.ShapeInject;
import com.iannotation.IElement;

/**
 * Created by lhz on 16/5/6.
 */
@IElement("customer")
public class ShapeInjectFragment extends DefaultFragment {

    @NonNull
    public static ShapeInjectFragment newInstance(String id) {
        Bundle arguments = new Bundle();
        ShapeInjectFragment fragment = new ShapeInjectFragment();
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

        Button btnRoundCode = findViewById(R.id.tv_round_code);
        int roundColor = mContext.getResources().getColor(R.color.c_46b942);
        int pressBgColor = getColor(R.color.c_994897fa);
        int disableBgColor = getColor(R.color.c_cc999999);
        int normalBgColor = getColor(R.color.c_3b8fed);
        ShapeInject.inject(btnRoundCode).setShapeType(ShapeInject.TYPE_ROUND).setStroke(2, roundColor)
                .setBackgroundColor(pressBgColor, disableBgColor, normalBgColor).background();

        TextView tvRoundRectCode = findViewById(R.id.tv_round_rect_code);
        int roundRectColor = mContext.getResources().getColor(R.color.c_4897fa);
        ShapeInject.inject(tvRoundRectCode).setShapeType(ShapeInject.TYPE_ROUND_RECT).setStroke(2, roundRectColor).background();

        TextView tvSegmentCode = findViewById(R.id.tv_segment_code);
        int segmentColor = mContext.getResources().getColor(R.color.c_ff4141);
        ShapeInject.inject(tvSegmentCode).setShapeType(ShapeInject.TYPE_SEGMENT).setStroke(2, segmentColor).background();

        TextView tvRadiusCode = findViewById(R.id.tv_radius_code);
        int radiusColor = mContext.getResources().getColor(R.color.c_ff00ff);
        int radius = (int) mContext.getResources().getDimension(R.dimen.dp_10);
        ShapeInject.inject(tvRadiusCode).setRadius(radius).setStroke(2, radiusColor).background();

        TextView tvDashGapCode = findViewById(R.id.tv_dash_gap_code);
        int dashGapPressedColor = mContext.getResources().getColor(R.color.c_f7b218);
        int dashGapNormalColor = mContext.getResources().getColor(R.color.c_00ffff);
        int dashGapSize = (int) mContext.getResources().getDimension(R.dimen.dp_5);
        ShapeInject.inject(tvDashGapCode).setRadius(dashGapSize)
                .setStroke(2, dashGapPressedColor, dashGapNormalColor, dashGapSize, dashGapSize)
                .background();

        LinearLayout llAllCode = findViewById(R.id.ll_all_code);
        TextView tvAllCode = findViewById(R.id.tv_all_code);

        int llNormalBgColor = getColor(R.color.c_4897fa);
        int llPressedBgColor = getColor(R.color.c_ff4141);
        int llNormalTextColor = getColor(R.color.c_fdbc40);
        int llPressedTextColor = getColor(R.color.c_34c749);
        int llStrokeNormalColor = getColor(R.color.c_ff4141);
        int llStrokePressColor = getColor(R.color.c_4897fa);
        int llSize = (int) mContext.getResources().getDimension(R.dimen.dp_10);
        int llStrokeWidth = (int) mContext.getResources().getDimension(R.dimen.dp_2);
        float[] radii = new float[]{llSize, llSize, 0, 0, llSize, llSize, 0, 0};
        ShapeInject.inject(llAllCode)
                .setBackgroundColor(llPressedBgColor, llPressedBgColor, llNormalBgColor)
                .setStroke(llStrokeWidth, llStrokePressColor, llStrokeNormalColor)
                .setTextColor(llPressedTextColor, llPressedTextColor, llNormalTextColor, tvAllCode)
                .setRadii(radii)
                .background1();
    }

    private int getColor(int colorRes) {
        return mContext.getResources().getColor(colorRes);
    }

}
