package com.ddu.ui.fragment.study.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ddu.R;
import com.ddu.icore.rx.activityresult.ActivityResultInfo;
import com.ddu.icore.rx.activityresult.RxActivityResult;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * Created by yzbzz on 2017/11/14.
 */

public class CameraFragment extends DefaultFragment implements View.OnClickListener {

    final private static int CAMERA_REQUEST_CODE = 1;

    private Button btnCamera;
    private ImageView ivPhoto;

    private RxPermissions rxPermissions;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    public void initView() {
        ivPhoto = findViewById(R.id.iv_photo);
        btnCamera = findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);
        setDefaultTitle("拍照");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(mActivity);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_camera:
                if (!rxPermissions.isGranted(Manifest.permission.CAMERA)) {
                    rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                getPhoto();
                            } else {
                                ToastUtils.showToast("没有拍照权限");
                            }
                        }
                    });
                } else {
                    getPhoto();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mFile && mFile.exists()) {
            mFile.delete();
        }
    }

    private void getPhoto() {
        RxActivityResult.with(mActivity)
                .startActivityForResult(startCamera(mActivity), CAMERA_REQUEST_CODE)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
//                        String path = GetImagePath.getPath(mContext, doSomething());
                        showPhoto();
                        ToastUtils.showToast("拍照成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast("拍照失败: " + throwable.getMessage());
                    }
                });
    }

    private void showPhoto() {
        if (null != mFile) {
            Glide.with(this).load(mFile).into(ivPhoto);
        }
    }

    private Uri photoUri;
    private File mFile;

    protected Intent startCamera(Activity mContext) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFile = new File(Environment.getExternalStorageDirectory().getPath(), "ddu_photo_temp" + System.currentTimeMillis() + ".png");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            photoUri = Uri.fromFile(mFile);
        } else {
            photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", mFile);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        Log.v("lhz", "photoUri: " + photoUri);
        return intent;
    }

    private Uri doSomething() {
        Uri inputUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            inputUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", mFile);//通过FileProvider创建一个content类型的Uri
        } else {
            inputUri = Uri.fromFile(mFile);
        }
        Log.v("lhz", "inputUri: " + inputUri);
        return inputUri;
    }


    public String getAbsPath(Activity activity, Intent data) {
        Uri uri;
        String picturePath = null;
        if (data == null || data.getData() == null) {
            uri = photoUri;
        } else {
            uri = data.getData();
        }
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        if (uri == null || filePathColumns == null) {
            return "";
        }
        final String scheme = uri.getScheme();
        if (scheme == null) {
            picturePath = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            picturePath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

        }
        Cursor c = activity.getContentResolver().query(uri, filePathColumns, null, null, null);
        if (null != c) {
            if (c.moveToFirst()) {
                picturePath = c.getString(c.getColumnIndex(filePathColumns[0]));
            }
            c.close();
        }
        return picturePath;
    }
}
