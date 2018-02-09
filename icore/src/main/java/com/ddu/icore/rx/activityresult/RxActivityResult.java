package com.ddu.icore.rx.activityresult;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;

import io.reactivex.Observable;

/**
 * Created by yzbzz on 2018/1/5.
 */

public class RxActivityResult {

    static final String TAG = "RxActivityResult";

    RxActivityResultFragment mRxActivityResultFragment;

    private RxActivityResult(@NonNull Activity activity) {
        mRxActivityResultFragment = getRxActivityResultFragment(activity);
    }

    private RxActivityResultFragment getRxActivityResultFragment(Activity activity) {
        RxActivityResultFragment rxActivityResultFragmentFragment = findRxPermissionsFragment(activity);
        boolean isNewInstance = rxActivityResultFragmentFragment == null;
        if (isNewInstance) {
            rxActivityResultFragmentFragment = new RxActivityResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(rxActivityResultFragmentFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return rxActivityResultFragmentFragment;
    }

    private RxActivityResultFragment findRxPermissionsFragment(Activity activity) {
        return (RxActivityResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public Observable<ActivityResultInfo> startActivityForResult(Intent intent, int requestCode) {
        return mRxActivityResultFragment.startForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(mRxActivityResultFragment.getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    public static RxActivityResult with(Activity activity) {
        return new RxActivityResult(activity);
    }

}
