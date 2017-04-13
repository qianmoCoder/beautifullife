package com.ddu.ui.fragment.study.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.sys.ViewUtils;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lhz on 16/5/6.
 */
public class ImageFragment extends DefaultFragment {

    private Unbinder unbinder;

    private boolean isBigModel = true;

    @BindView(R.id.iv_common)
    ImageView mIvCommon;

    @NonNull
    public static ImageFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_image;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);

        setDefaultTitle("Image");
        setRightText("小图", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleBar.getRightText().setText(isBigModel ? "小图" : "大图");
                setImg(isBigModel);
                isBigModel = !isBigModel;
            }
        });
    }

    private void setImg(boolean isBigModel) {
        int resId = isBigModel ? R.drawable.home_icon_glod_card : R.drawable.guide_hand;
        for (int i = 1; i < 10; i++) {
            ImageView imageView = ViewUtils.findViewById(mView, getResId("iv" + i, R.id.class));
            imageView.setImageResource(resId);
        }
        mIvCommon.setImageResource(resId);
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
