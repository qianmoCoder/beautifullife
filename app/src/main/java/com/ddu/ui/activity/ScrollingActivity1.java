package com.ddu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.ddu.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ScrollingActivity1 extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    PtrClassicFrameLayout ptrFrameLayout;
    private int mVerticalOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling1);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mVerticalOffset = verticalOffset;
                Log.v("lhz", "verticalOffset " + verticalOffset);
            }
        });
        
        PtrHandler ptrHandler = new PtrHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return mVerticalOffset == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                currentPage = 1;
//                initDates();
            }
        };
        ptrFrameLayout.setPtrHandler(ptrHandler);
    }
}
