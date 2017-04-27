package com.ddu.ui.activity;

import android.content.Intent;
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
public class LoginActivity2 extends BaseActivity {

    private Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_work_state);

        mBtnOk = ViewUtils.findViewById(this, R.id.btn_ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginActivity1.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setDefaultTitle("two");
    }

    public void login() {
        GodIntent msg = new GodIntent();
        msg.setAction(1);
        msg.putString("", "");
        msg.putString("", "");
    }

}
