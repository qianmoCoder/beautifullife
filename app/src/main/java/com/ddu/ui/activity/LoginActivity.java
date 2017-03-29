package com.ddu.ui.activity;

import android.os.Bundle;

import com.ddu.icore.aidl.GodIntent;
import com.ddu.icore.ui.activity.BaseActivity;

/**
 * Created by liuhongzhe on 16/5/13.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void login(){
        GodIntent msg = new GodIntent();
        msg.setAction(1);
        msg.putString("","");
        msg.putString("","");
    }

}
