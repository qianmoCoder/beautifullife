package com.ddu.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by yzbzz on 2018/2/5.
 */

public class SchemeFilterActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        Log.v("lhz","scheme: " + uri.getScheme());
        Log.v("lhz","host: " + uri.getHost());
//        Log.v("lhz","host: " + uri.getEncodedQuery());
//        Log.v("lhz","host: " + uri.getEncodedAuthority());
//        Log.v("lhz","host: " + uri.getEncodedPath());
//        Log.v("lhz","host: " + uri.getEncodedSchemeSpecificPart());
//        Log.v("lhz","host: " + uri.getSchemeSpecificPart());
        Log.v("lhz","path: " + uri.getPath());
        Log.v("lhz","toString: " + uri.toString());
    }
}
