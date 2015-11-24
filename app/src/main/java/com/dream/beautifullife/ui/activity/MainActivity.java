package com.dream.beautifullife.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.beautifullife.core.util.DensityUtil;
import com.beautifullife.core.util.SystemBarTintManager;
import com.dream.beautifullife.R;
import com.dream.beautifullife.ui.adapter.MyAdapter;
import com.dream.beautifullife.ui.misc.DividerItemDecoration;
import com.dream.beautifullife.widget.ChannelUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    private boolean pendingIntroAnimation;
    private MenuItem settingMenuItem;

    @Bind(R.id.content)
    CoordinatorLayout mCoordinatorLayout;
    private View fabCreate;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.my_recycle_view)
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    MyAdapter myAdapter;

    @Bind(R.id.content_main)
    View contentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        RxView.clicks(fab).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Snackbar.make(mCoordinatorLayout, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }

        Toast.makeText(this, ChannelUtil.getChannel(this), Toast.LENGTH_SHORT).show();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        final List<String> tempList = new ArrayList<>();

        Observable.range(3, 50).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tempList.add(integer + "");
            }
        });
        myAdapter = new MyAdapter(tempList);
        recyclerView.setAdapter(myAdapter);
        float paddingStart = 72;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, paddingStart, true));
    }

    private void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setTintColor(getResources().getColor(R.color.colorAccent));

        }

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initView() {
        fabCreate = findViewById(R.id.container);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        settingMenuItem = menu.findItem(R.id.action_settings);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
//        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));

        int actionbarSize = DensityUtil.dpToPx(56);
        toolbar.setTranslationY(-actionbarSize);
//        getIvLogo().setTranslationY(-actionbarSize);
//        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
//        getIvLogo().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(400);
//        getInboxMenuItem().getActionView().animate()
//                .translationY(0)
//                .setDuration(ANIM_DURATION_TOOLBAR)
//                .setStartDelay(500)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        startContentAnimation();
//                    }
//                })
//                .start(); View shape = rootView.findViewById(R.id.circle);

        // Create a reveal {@link Animator} that starts clipping the view from
        // the top left corner until the whole view is covered.
//        Animator animator = ViewAnimationUtils.createCircularReveal(
//                contentMain,
//                0,
//                0,
//                0,
//                (float) Math.hypot(contentMain.getWidth(), contentMain.getHeight()));
//
//        // Set a natural ease-in/ease-out interpolator.
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        // Finally start the animation
//        animator.start();

        contentMain.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();

    }

    private void startContentAnimation() {
        fabCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
//        feedAdapter.updateItems(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
