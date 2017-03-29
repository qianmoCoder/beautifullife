package com.ddu.ui.fragment.study.ui;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.dialog.ColorPickerDialog;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/14.
 */
public class ShapeFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.btn_style)
    Button btnStyle;

    @Nullable
    @BindView(R.id.et_stroke)
    EditText etStroke;
    @Nullable
    @BindView(R.id.et_round_radius)
    EditText etRoundRadius;
    @Nullable
    @BindView(R.id.et_angle)
    EditText etAngle;

    @Nullable
    @BindView(R.id.btn_stroke_color)
    Button btnStrokeColor;
    @Nullable
    @BindView(R.id.btn_begin_color)
    Button btnBeginColor;
    @Nullable
    @BindView(R.id.btn_middle_color)
    Button btnMiddleColor;
    @Nullable
    @BindView(R.id.btn_end_color)
    Button btnEndColor;


    @BindColor(R.color.c_252525)
    int beginColor;
    @BindColor(R.color.c_3e7492)
    int middleColor;
    @BindColor(R.color.c_a6c0cd)
    int endColor;
    @BindColor(R.color.c_2e3135)
    int strokeColor;

    private int strokeWidth;
    private int roundRadius;

    private Unbinder unbinder;

    @NonNull
    public static ShapeFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        ShapeFragment fragment = new ShapeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    //    private void shape() {
//        int strokeWidth = 5; // 3dp 边框宽度
//        int roundRadius = 15; // 8dp 圆角半径
//        int strokeColor = Color.parseColor("#2E3135");//边框颜色
//        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
//
//        GradientDrawable gd = new GradientDrawable();//创建drawable
//        gd.setColor(fillColor);
//        gd.setCornerRadius(roundRadius);
//        gd.setStroke(strokeWidth, strokeColor);
//        btnShow.setBackground(gd);
//
//        int colors[] = {0xff255779, 0xff3e7492, 0xffa6c0cd};//分别为开始颜色，中间夜色，结束颜色
//
//        GradientDrawable gd1 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//
//    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_shape;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_stroke_color, R.id.btn_begin_color, R.id.btn_middle_color, R.id.btn_end_color, R.id.btn_style})
    public void onClick(@NonNull View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_style) {
            setBackground();
        } else {
            showColorDialog(view);
        }
    }

    private void setBackground() {
        roundRadius = getValueFromEditText(etRoundRadius);
        strokeWidth = getValueFromEditText(etStroke);
        int colors[] = {beginColor, middleColor, endColor};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);//创建drawable
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setGradientRadius(3);
        btnStyle.setBackground(gd);
    }

    private void showColorDialog(@NonNull final View view) {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(mContext, new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {
                int id = view.getId();
                view.setBackgroundColor(color);
                if (id == R.id.btn_begin_color) {
                    beginColor = color;
                } else if (id == R.id.btn_middle_color) {
                    middleColor = color;
                } else if (id == R.id.btn_end_color) {
                    endColor = color;
                } else if (id == R.id.btn_stroke_color) {
                    strokeColor = color;
                }
            }
        }, Color.RED);
        colorPickerDialog.show();
    }

    private int getValueFromEditText(@NonNull EditText editText) {
        String text = editText.getText().toString().trim();
        int result;
        try {
            result = Integer.parseInt(text);
        } catch (Exception e) {
            result = 1;
        }
        return result;
    }
}
