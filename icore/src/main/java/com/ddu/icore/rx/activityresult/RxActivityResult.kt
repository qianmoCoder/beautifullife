package com.ddu.icore.rx.activityresult

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable

/**
 * Created by yzbzz on 2018/1/5.
 */

class RxActivityResult private constructor(activity: Activity) {

    var mRxActivityResultFragment: RxActivityResultFragment

    init {
        mRxActivityResultFragment = getRxActivityResultFragment(activity)
    }

    private fun getRxActivityResultFragment(activity: Activity): RxActivityResultFragment {
        var rxActivityResultFragmentFragment: RxActivityResultFragment = findRxFragment(activity)
        val isNewInstance = rxActivityResultFragmentFragment == null
        if (isNewInstance) {
            rxActivityResultFragmentFragment = RxActivityResultFragment()
            val fragmentManager = activity.fragmentManager
            fragmentManager
                    .beginTransaction()
                    .add(rxActivityResultFragmentFragment, TAG)
                    .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return rxActivityResultFragmentFragment
    }

    private fun findRxFragment(activity: Activity): RxActivityResultFragment {
        return activity.fragmentManager.findFragmentByTag(TAG) as RxActivityResultFragment
    }

    fun startActivityForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo> {
        return mRxActivityResultFragment.startForResult(intent, requestCode)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(mRxActivityResultFragment.activity, cls)
        startActivityForResult(intent, requestCode)
    }

    companion object {

        internal val TAG = "RxActivityResult"

        fun with(activity: Activity): RxActivityResult {
            return RxActivityResult(activity)
        }
    }

}
