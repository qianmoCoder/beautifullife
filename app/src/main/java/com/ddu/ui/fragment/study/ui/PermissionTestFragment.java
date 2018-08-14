package com.ddu.ui.fragment.study.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.logic.Actions;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.ui.help.ShapeInjectHelper;
import com.ddu.icore.util.sys.DownloadManagerUtils;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 16/4/14.
 */
@IElement("UI")
public class PermissionTestFragment extends DefaultFragment {

    public static final int INSTALL_PACKAGES_REQUESTCODE = 0x0010;
    public static final int ACTION_MANAGE_UNKNOWN_APP_SOURCES = 0x0020;

    public static final int GET_UNKNOWN_APP_SOURCES = 0x00010;

    private int downLoadId = -1;

    private Button mBtnStart;
    private Button mBtnStop;
    private EditText mEtText;
    private LinearLayout mLLItems;

    @NonNull
    public static PermissionTestFragment newInstance(String taskId) {
        Bundle arguments = new Bundle();
        arguments.putString(Companion.getARGUMENT_TASK_ID(), taskId);
        PermissionTestFragment fragment = new PermissionTestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        registerObserver();
    }

    @Override
    public void onReceiverNotify(GodIntent godIntent) {
        int action = godIntent.getAction();
        if (action == Actions.DOWNLOAD_COMPLETE) {
            downLoadId = godIntent.getKey("downloadId");
            checkIsAndroidO();
        }
    }

    private void checkIsAndroidO() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean b = getMContext().getPackageManager().canRequestPackageInstalls();
            if (b) {
                DownloadManagerUtils.startInstall(getMContext(), "icore.apk");
            } else {
                //请求安装未知应用来源的权限
//                requestPermissions(new String[]{Manifest.permission.WRITE_SETTINGS}, INSTALL_PACKAGES_REQUESTCODE);
                requestPermissions(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_PACKAGES_REQUESTCODE);
//                requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
            }
        } else {
            DownloadManagerUtils.startInstall(getMContext(), "icore.apk");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INSTALL_PACKAGES_REQUESTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadManagerUtils.startInstall(getMContext(), downLoadId);
                } else {
                    gotoSetting();
                }
                break;
//            case ACTION_MANAGE_UNKNOWN_APP_SOURCES:
//                gotoSetting();
//                break;
        }
    }

    private void gotoSetting() {
        boolean b = ContextCompat.checkSelfPermission(getMContext(), Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        if (b) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
            startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
        } else {
            ActivityCompat.requestPermissions(getMActivity(), new String[]{Manifest.permission.WRITE_SETTINGS}, ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_UNKNOWN_APP_SOURCES) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui_shape_advanced;
    }

    @Override
    public void initView() {
        mLLItems = findViewById(R.id.ll_items);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        mEtText = findViewById(R.id.et_count);

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIsAndroidO();
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkIsAndroidO();
//                gotoSetting();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
    }
}
