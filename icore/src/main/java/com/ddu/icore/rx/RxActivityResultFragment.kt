package com.ddu.acitvityresult

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by yzbzz on 2018/1/17.
 */
class RxActivityResultFragment : Fragment() {
    private val mSubjects = mutableMapOf<Int, PublishSubject<ActivityResultInfo>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var publishSubject = mSubjects.remove(requestCode)
        publishSubject?.let {
            var activityResultInfo = ActivityResultInfo(requestCode, resultCode, data)
            publishSubject.onNext(activityResultInfo)
            publishSubject.onComplete()

        }
    }

    fun startForResult(intent: Intent, requestCode: Int): Observable<ActivityResultInfo> {
        var publishSubject = PublishSubject.create<ActivityResultInfo>()
        mSubjects.put(requestCode, publishSubject)
        return publishSubject.doOnSubscribe {
            startActivityForResult(intent, requestCode)
        }
    }
}