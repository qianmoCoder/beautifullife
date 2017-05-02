package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.view.ShapeButton;
import com.ddu.icore.util.sys.ViewUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class DrawFragment extends DefaultFragment {

    private Unbinder unbinder;
    private ShapeButton shapeTextView;

    @NonNull
    public static DrawFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
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
        unbinder = ButterKnife.bind(this, mView);
        shapeTextView = ViewUtils.findViewById(mView, R.id.different_radius_test);
//        shapeTextView.setRadius(new float[]{0, 0, 20, 20, 40, 40, 60, 60});
        int height = shapeTextView.getHeight();
//        shapeTextView.getShapeInject().setSegmented(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
