package com.ddu.icore.rx.activityresult


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by yzbzz on 2018/1/5.
 */

class RxActivityResultFragment : Fragment() {

    private val mSubjects = HashMap<Int, PublishSubject<ActivityResultInfo>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val publishSubject = mSubjects.remove(requestCode)
        if (null != publishSubject) {
            val activityResultInfo = ActivityResultInfo()
            activityResultInfo.requestCode = requestCode
            activityResultInfo.resultCode = resultCode
            activityResultInfo.data = data
            publishSubject.onNext(activityResultInfo)
            publishSubject.onComplete()
        }
    }

    fun startForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo> {
        val publishSubject = PublishSubject.create<ActivityResultInfo>()
        mSubjects[requestCode] = publishSubject
        return publishSubject.doOnSubscribe { startActivityForResult(intent, requestCode) }

    }
}
