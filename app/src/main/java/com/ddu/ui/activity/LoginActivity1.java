package com.ddu.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        setContentView(R.layout.fragment_work_state);

        Uri uri = getIntent().getData();
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void login() {
        GodIntent msg = new GodIntent();
        msg.setAction("1");
        msg.putString("", "");
        msg.putString("", "");
    }

}
