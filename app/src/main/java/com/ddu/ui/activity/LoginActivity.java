package com.ddu.ui.activity;

import android.os.Bundle;

import com.ddu.R;
import com.ddu.icore.ui.activity.BaseActivity;

/**
 * Created by liuhongzhe on 16/5/13.
 */
public class LoginActivity extends BaseActivity {

//    private RelativeLayout mRlLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
//        mRlLogin = ViewUtils.findViewById(this, R.id.rl_login);
//        ShapeInject shapeInjectHelper = ShapeInject.inject(mRlLogin);
//        shapeInjectHelper.setBackgroundColor(0xffffff);
//        shapeInjectHelper.setRadius(5);
//        shapeInjectHelper.background();
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
