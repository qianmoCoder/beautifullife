package com.ddu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ddu.R;

public class ScrollingActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    LinearLayout ll_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.v("lhz","verticalOffset: " + verticalOffset);
                RelativeLayout.LayoutParams layoutParams = ((RelativeLayout.LayoutParams) ll_search.getLayoutParams());
                float rightMargin = -verticalOffset;
                layoutParams.rightMargin = (int) rightMargin;
                ll_search.setLayoutParams(layoutParams);

            }
        });
    }
}
