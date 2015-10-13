package com.dream.beautifullife.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dream.beautifullife.app.BeautifulLifeApplication;

/**
 * Created by admin on 2015/9/24.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BeautifulLifeApplication.addActivity(this);
        setContentView(getContentViewId());
        Log.v(TAG, "className: " + getClass().getName());
    }

    public abstract int getContentViewId();

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

    private Intent getFragmentIntent(Class<?> cls, Bundle bundle){
        Intent intent = new Intent(this, ShowDetailActivity.class);
        if (cls != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("fragmentName", cls.getName());
            intent.putExtras(bundle);
        }
        return intent;
    }

    public void startFragment(Class<?> cls, Bundle bundle) {
        startActivity(getFragmentIntent(cls,bundle));
    }

    public void startFragmentForResult(Class<?> cls, Bundle bundle, int requestCode, Activity ac) {
        ac.startActivityForResult(getFragmentIntent(cls,bundle), requestCode);
    }

    public void startFragmentForResult(Class<?> cls, Bundle bundle, int requestCode, Fragment fragment) {
        fragment.startActivityForResult(getFragmentIntent(cls,bundle), requestCode);
    }

    public void startActivityForResult(Intent intent, int requestCode, Fragment fragment) {
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BeautifulLifeApplication.removeActivity(this);
    }
}
