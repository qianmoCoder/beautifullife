package com.ddu.ui.fragment.study.ui;

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

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.util.GetImagePath;

import java.io.File;

/**
 * Created by yzbzz on 2017/11/14.
 */

public class CameraFragment extends DefaultFragment implements View.OnClickListener {

    final private static int CAMERA_REQUEST_CODE = 1;

    private Button btnCamera;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    public void initView() {
        btnCamera = findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_camera:
                startActivityForResult(startCamera(mActivity), CAMERA_REQUEST_CODE);
                break;
        }
    }

    private Uri photoUri;
    private File mFile;

    protected Intent startCamera(Activity mContext) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mFile = new File(Environment.getExternalStorageDirectory().getPath(), "ddu_photo_temp" + System.currentTimeMillis() + ".png");
//        if (android.os.Build.VERSION.SDK_INT < 24) {
            photoUri = Uri.fromFile(mFile);
//        } else {
//            photoUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", mFile);
////            ContentValues contentValues = new ContentValues(1);
////            contentValues.put(MediaStore.Images.Media.DATA, mFile.getAbsolutePath());
////            photoUri = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//
//        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        Log.v("lhz", "photoUri: " + photoUri);
        return intent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                String path = GetImagePath.getPath(mContext, doSomething());
                Log.v("lhz", "path: " + path);
                break;
        }
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
