package com.ddu.icore.rx.activityresult


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by yzbzz on 2018/1/5.
 */

class RxActivityResultFragment : androidx.fragment.app.Fragment() {

    @SuppressLint("UseSparseArrays")
    private val mSubjects = HashMap<Int, PublishSubject<ActivityResultInfo>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val publishSubject = mSubjects.remove(requestCode)
        publishSubject?.run {
            val activityResultInfo = ActivityResultInfo()
            activityResultInfo.requestCode = requestCode
            activityResultInfo.resultCode = resultCode
            activityResultInfo.data = data
            onNext(activityResultInfo)
            onComplete()
        }
    }

    fun startForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo> {
        val publishSubject = PublishSubject.create<ActivityResultInfo>()
        mSubjects[requestCode] = publishSubject
        return publishSubject.doOnSubscribe { startActivityForResult(intent, requestCode) }

    }
}
