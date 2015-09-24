package com.dream.beautifullife.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.dream.beautifullife.R;

/**
 * Created by admin on 2015/9/24.
 */
public class ShowDetailActivity extends BaseActivity {

    public static final int FRAGMENT_ADD = 1;
    public static final int FRAGMENT_REPLACE = 2;

    private Intent intent;
    private Bundle bundle;
    public String fragmentName;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
        }
        if (bundle != null) {
            fragmentName = bundle.getString("fragmentName");
        }

        setContentView(R.layout.show_detail);

        addFragmentToStack(fragmentName,FRAGMENT_ADD);
    }

    private void addFragmentToStack(String fragmentName, int type) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = null;
        try {
            newFragment = (Fragment) Class.forName(fragmentName).newInstance();
            if (bundle != null) {
                newFragment.setArguments(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ft.setCustomAnimations(R.anim.activity_alpha_in2, R.anim.activity_alpha_out2, R.anim.activity_alpha_in, R.anim.activity_alpha_out);
        switch (type) {
            case FRAGMENT_ADD:
                ft.replace(R.id.container, newFragment, fragmentName);
                break;
            case FRAGMENT_REPLACE:
                ft.replace(R.id.container, newFragment, fragmentName);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                break;
        }
        ft.commitAllowingStateLoss();
    }
}
