package com.ddu.icore.rx.activityresult

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable

/**
 * Created by yzbzz on 2018/1/5.
 */

class RxActivityResult private constructor(activity: androidx.fragment.app.FragmentActivity) {

    private var mRxActivityResultFragment: RxActivityResultFragment

    init {
        mRxActivityResultFragment = getRxActivityResultFragment(activity)
    }

    private fun getRxActivityResultFragment(activity: androidx.fragment.app.FragmentActivity): RxActivityResultFragment {
        var rxActivityResultFragmentFragment: RxActivityResultFragment? = findRxFragment(activity)
        val isNewInstance = rxActivityResultFragmentFragment == null
        if (isNewInstance) {
            rxActivityResultFragmentFragment = RxActivityResultFragment()
            val fragmentManager = activity.supportFragmentManager
            fragmentManager
                    .beginTransaction()
                    .add(rxActivityResultFragmentFragment, TAG)
                    .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return rxActivityResultFragmentFragment!!
    }

    private fun findRxFragment(activity: androidx.fragment.app.FragmentActivity): RxActivityResultFragment? {
        return activity.supportFragmentManager.findFragmentByTag(TAG) as? RxActivityResultFragment
    }

    fun startActivityForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo> {
        return mRxActivityResultFragment.startForResult(intent, requestCode)
    }

    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(mRxActivityResultFragment.activity, cls)
        startActivityForResult(intent, requestCode)
    }

    companion object {

        internal const val TAG = "RxActivityResult"

        fun with(activity: androidx.fragment.app.FragmentActivity): RxActivityResult {
            return RxActivityResult(activity)
        }
    }

}
