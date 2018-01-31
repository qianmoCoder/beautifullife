package com.ddu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.ui.activity.BaseActivity;

/**
 * Created by liuhongzhe on 16/5/13.
 */
public class LoginActivity1 extends BaseActivity {

    private Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("lhz", "onCreate");
        setContentView(R.layout.fragment_work_state);
        Intent intent = getIntent();

//        Uri uri = getIntent().getData();
//        String scheme = uri.getScheme();
//        String host = uri.getHost();
//        String path = uri.getPath();
//        Log.v("lhz", "toString: " + uri.toString());
//        Log.v("lhz", "getUserInfo: " + uri.getUserInfo());
//        Log.v("lhz", "scheme: " + scheme + " host: " + host + " path: " + path + " " + uri.getPort());
//
//        mBtnOk = ViewUtils.findViewById(this, R.id.btn_ok);
//        mBtnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mContext, LoginActivity2.class));
//            }
//        });
//        setDefaultTitle("one");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("lhz", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("lhz", "onStart");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("lhz", "onNewIntent");
    }

    public void login() {
        GodIntent msg = new GodIntent();
        msg.setAction(1);
        msg.putString("", "");
        msg.putString("", "");
    }

}
