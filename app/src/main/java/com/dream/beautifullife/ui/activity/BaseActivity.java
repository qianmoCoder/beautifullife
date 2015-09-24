package com.dream.beautifullife.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.dream.beautifullife.app.BeautifulLifeApplication;

/**
 * Created by admin on 2015/9/24.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BeautifulLifeApplication.addActivity(this);
        Log.v("lhz", "className: " + getClass().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BeautifulLifeApplication.removeActivity(this);
    }
}
