package com.ddu.ui.fragment.study.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.help.ShapeInjectHelper;
import com.ddu.icore.util.sys.DownloadManagerUtils;

/**
 * Created by yzbzz on 16/4/14.
 */
public class ShapeAdvancedFragment extends DefaultFragment {

    private Button mBtnStart;
    private EditText mEtText;
    private LinearLayout mLLItems;

    @NonNull
    public static ShapeAdvancedFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TASK_ID, taskId);
        ShapeAdvancedFragment fragment = new ShapeAdvancedFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_shape_advanced;
    }

    @Override
    public void initView() {
        mLLItems = findViewById(R.id.ll_items);
        mBtnStart = findViewById(R.id.btn_start);
        mEtText = findViewById(R.id.et_count);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManagerUtils.downLoad(mContext,"etcp.apk","http://7xj3cg.com2.z0.glb.qiniucdn.com/ETCP_Android.apk");
                mLLItems.removeAllViews();
                int count = Integer.parseInt(mEtText.getText().toString());
                int resId = R.layout.fragment_ui_common_textview;
                for (int i = 0; i < count; i++) {
                    final TextView linearLayout = (TextView) getLayoutInflater().inflate(resId, null);
                    ShapeInjectHelper shapeInjectHelper = new ShapeInjectHelper(linearLayout);
                    if (i == 0) {
                        shapeInjectHelper.shapeType(ShapeInjectHelper.SEGMENT);
                        shapeInjectHelper.shapeDirection(ShapeInjectHelper.DIRECTION_TOP);
                        shapeInjectHelper.radius(5);
                    }

                    if (i == count - 1) {
                        shapeInjectHelper.shapeType(ShapeInjectHelper.SEGMENT);
                        shapeInjectHelper.shapeDirection(ShapeInjectHelper.DIRECTION_BOTTOM);
                        shapeInjectHelper.radius(5);
                    }
                    shapeInjectHelper.setBackground();
                    linearLayout.setOnClickListener(new View.OnClickListener() {

                        boolean isCheck = true;

                        @Override
                        public void onClick(View v) {
                            if (isCheck) {
                                linearLayout.setBackgroundColor(Color.RED);
                            } else {
                                linearLayout.setBackgroundColor(Color.WHITE);
                            }
                            isCheck = !isCheck;
                        }
                    });
                    mLLItems.addView(linearLayout);
                }
            }
        });
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
