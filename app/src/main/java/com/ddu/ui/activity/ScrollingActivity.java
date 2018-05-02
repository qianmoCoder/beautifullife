package com.ddu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ddu.R;
import com.ddu.icore.util.sys.DensityUtils;

public class ScrollingActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    LinearLayout ll_search;
    RelativeLayout rl_title_bar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CoordinatorLayout coordinatorLayout;
    NestedScrollView nestedScrollView;
    private int offsetY;

    private boolean isExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        offsetY = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        coordinatorLayout = findViewById(R.id.home_main);
        nestedScrollView = findViewById(R.id.scroll_view);
        appBarLayout = findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        ll_search = findViewById(R.id.ll_search);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                appBarLayout.setExpanded(false);
                collapsingToolbarLayout.setScrimsShown(false);
            }
        });
        rl_title_bar = findViewById(R.id.rl_title_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        int a = DensityUtils.px2dip(this, 132);
        int b = DensityUtils.px2dip(this, 162);
        int c = DensityUtils.px2dip(this, 102);
        Log.v("lhz", "a: " + a + " b: " + b + " c: " + c);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (isExpanded) {
//                    if (verticalOffset <= -10) {
//                        appBarLayout.setExpanded(false);
//                        isExpanded = false;
//                    }
//                }
                Log.v("lhz","appBarLayout.getTotalScrollRange(): " + appBarLayout.getTotalScrollRange());
                int rightMargin = Math.abs(verticalOffset);
                rl_title_bar.setPadding(offsetY, offsetY, rightMargin + offsetY, offsetY);
            }
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }
}
