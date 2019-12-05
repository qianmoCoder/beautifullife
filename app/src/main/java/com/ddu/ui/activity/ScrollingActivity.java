package com.ddu.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.ddu.R;
import com.ddu.icore.util.sys.DensityUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.home_main);
        nestedScrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                appBarLayout.setExpanded(false);
                collapsingToolbarLayout.setScrimsShown(false);
            }
        });
        rl_title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        int a = DensityUtils.px2dip(this, 132);
        int b = DensityUtils.px2dip(this, 162);
        int c = DensityUtils.px2dip(this, 102);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (isExpanded) {
//                    if (verticalOffset <= -10) {
//                        appBarLayout.setExpanded(false);
//                        isExpanded = false;
//                    }
//                }
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
