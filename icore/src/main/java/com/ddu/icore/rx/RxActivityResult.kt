package com.ddu.acitvityresult

import android.app.Activity
import android.content.Intent
import io.reactivex.Observable

/**
 * Created by yzbzz on 2018/1/17.
 */
class RxActivityResult(var activity: Activity) {

    companion object {
        val TAG = "RxActivityResult"
        fun with(activity: Activity): RxActivityResult = RxActivityResult(activity)
    }

    var mRxActivityResultFragment: RxActivityResultFragment

    init {
        mRxActivityResultFragment = getRxActivityResultFragment(activity)
    }

    private fun getRxActivityResultFragment(activity: Activity): RxActivityResultFragment {
        var rxActivityResultFragmentFragment: RxActivityResultFragment? = findRxPermissionsFragment(activity)
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
        return rxActivityResultFragmentFragment!!
    }

    fun findRxPermissionsFragment(activity: Activity): RxActivityResultFragment? {
        return activity.fragmentManager.findFragmentByTag(TAG) as RxActivityResultFragment
    }

    fun startActivityForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo>? {
        return mRxActivityResultFragment.startForResult(intent, requestCode)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        var intent = Intent(mRxActivityResultFragment.activity, cls)
        startActivityForResult(intent, requestCode)
    }
}