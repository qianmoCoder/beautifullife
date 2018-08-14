package com.ddu.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ddu.R;
import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.ui.activity.BaseActivity;
import com.ddu.icore.util.sys.ViewUtils;

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

        Uri uri = getIntent().getData();
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();
        Log.v("lhz", "toString: " + uri.toString());
        Log.v("lhz", "getUserInfo: " + uri.getUserInfo());
        Log.v("lhz", "scheme: " + scheme + " host: " + host + " path: " + path + " " + uri.getPort());

        mBtnOk = ViewUtils.findViewById(this, R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getMContext(), LoginActivity2.class));
            }
        });
        setDefaultTitle("one");
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
