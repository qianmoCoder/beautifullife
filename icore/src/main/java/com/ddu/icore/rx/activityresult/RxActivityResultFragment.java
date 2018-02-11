package com.ddu.icore.rx.activityresult;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by yzbzz on 2018/1/5.
 */

public class RxActivityResultFragment extends Fragment {

    private Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();

    public RxActivityResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PublishSubject<ActivityResultInfo> publishSubject = mSubjects.remove(requestCode);
        if (null != publishSubject) {
            ActivityResultInfo activityResultInfo = new ActivityResultInfo();
            activityResultInfo.setRequestCode(requestCode);
            activityResultInfo.setResultCode(resultCode);
            activityResultInfo.setData(data);
            publishSubject.onNext(activityResultInfo);
            publishSubject.onComplete();
        }
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent, final int requestCode) {
        PublishSubject<ActivityResultInfo> publishSubject = PublishSubject.create();
        mSubjects.put(requestCode, publishSubject);
        return publishSubject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                startActivityForResult(intent, requestCode);
            }
        });

    }
}
