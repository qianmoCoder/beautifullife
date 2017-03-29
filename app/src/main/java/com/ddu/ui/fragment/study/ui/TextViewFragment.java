package com.ddu.ui.fragment.study.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.ui.effect.CurveFloatingPathEffect;
import com.ddu.ui.effect.CurvePathFloatingAnimator;
import com.ddu.ui.effect.ScaleFloatingAnimator;
import com.ddu.ui.view.FloatingText;
import com.ddu.ui.view.RevealTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzbzz on 16/4/14.
 */
public class TextViewFragment extends DefaultFragment {


    @Nullable
    @BindView(R.id.tv_reveal_text_view)
    RevealTextView tvRevealTextView;
    @Nullable
    @BindView(R.id.tv_translateView)
    TextView tvTranslateView;
    @Nullable
    @BindView(R.id.tv_curveView)
    TextView tvCurveView;
    @Nullable
    @BindView(R.id.tv_scaleView)
    TextView tvScaleView;

    private Unbinder unbinder;

    @NonNull
    public static TextViewFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        TextViewFragment fragment = new TextViewFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_textview;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
    }

    @OnClick({R.id.tv_reveal_text_view, R.id.tv_translateView, R.id.tv_curveView, R.id.tv_scaleView})
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_reveal_text_view:
                tvRevealTextView.replayAnimation();
                break;
            case R.id.tv_translateView:
                showFloatingTextByTranslate(view);
                break;
            case R.id.tv_curveView:
                showFloatingTextByCurve(view);
                break;
            case R.id.tv_scaleView:
                showFloatingTextByScale(view);
                break;
            default:
                break;
        }
    }

    private void showFloatingTextByTranslate(View view) {
        FloatingText translateFloatingText = new FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.RED)
                .textSize(100)
                .textContent("+1000")
                .build();
        translateFloatingText.attach2Window();
        translateFloatingText.startFloating(view);
    }

    private void showFloatingTextByCurve(View view) {
        FloatingText cubicFloatingText = new FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.RED)
                .textSize(100)
                .floatingAnimatorEffect(new CurvePathFloatingAnimator())
                .floatingPathEffect(new CurveFloatingPathEffect())
                .textContent("Hello! ").build();
        cubicFloatingText.attach2Window();
        cubicFloatingText.startFloating(view);
    }

    private void showFloatingTextByScale(View view) {
        FloatingText scaleFloatingText = new FloatingText.FloatingTextBuilder(baseActivity)
                .textColor(Color.parseColor("#7ED321"))
                .textSize(100)
                .offsetY(-100)
                .floatingAnimatorEffect(new ScaleFloatingAnimator())
                .textContent("+188").build();
        scaleFloatingText.attach2Window();
        scaleFloatingText.startFloating(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
