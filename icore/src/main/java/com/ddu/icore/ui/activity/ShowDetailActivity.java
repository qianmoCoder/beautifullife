package com.ddu.icore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;

import com.ddu.icore.R;
import com.ddu.icore.ui.fragment.BaseFragment;
import com.ddu.icore.util.FragmentUtils;

public class ShowDetailActivity extends BaseActivity {

    public static final String KEY_FRAGMENT_NAME = "fragmentName";
    public static final String KEY_TYPE = "type";

    private Intent intent;
    private Bundle bundle;
    @Nullable
    private String fragmentName;
    private Fragment fragment;
    private int type = FragmentUtils.INSTANCE.getFRAGMENT_REPLACE();

    private FragmentManager mFragmentManager;

    private boolean isShowTitleBar = false;

    @NonNull
    public static Intent getShowDetailIntent(Context context, String fragmentName, @Nullable Bundle bundle) {
        Intent intent = new Intent(context, ShowDetailActivity.class);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(KEY_FRAGMENT_NAME, fragmentName);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.show_detail);

        mFragmentManager = getSupportFragmentManager();

        replaceFragment(fragment, bundle);
    }


    public void initData() {
        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle != null) {
            fragmentName = bundle.getString(KEY_FRAGMENT_NAME);
        }

        if (!TextUtils.isEmpty(fragmentName)) {
            try {
                fragment = (Fragment) Class.forName(fragmentName).newInstance();
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
                if (fragment instanceof BaseFragment) {
                    isShowTitleBar = ((BaseFragment) fragment).isShowActivityTitleBar();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void replaceFragment(@Nullable Fragment fragment, int type) {
        if (null != fragment) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            FragmentUtils.INSTANCE.attachFragment(type, ft, fragment, R.id.container);
        }
    }

    public void replaceFragment(@Nullable Fragment fragment, Bundle bundle) {
        if (null != fragment) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            if (null != bundle) {
                type = bundle.getInt(KEY_TYPE, FragmentUtils.INSTANCE.getFRAGMENT_REPLACE());
            }
            FragmentUtils.INSTANCE.attachFragment(type, ft, fragment, R.id.container);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean isShowTitleBar() {
        return isShowTitleBar;
    }
}
