package com.ddu.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.iannotation.model.RouteMeta;

/**
 * Created by yzbzz on 2018/11/7.
 */
public class StudyViewModel extends ViewModel {

    private Context mContext;
    private MutableLiveData<Float> mRadius = new MutableLiveData<>();
    private MutableLiveData<RouteMeta> mRouteMeta = new MutableLiveData<>();


    public StudyViewModel(Context context, RouteMeta routeMeta) {
        mContext = context;
        mRouteMeta.setValue(routeMeta);
        mRadius.setValue(20.0f);
    }

    public RouteMeta getRoueMeta() {
        return mRouteMeta.getValue();
    }

    public Float getRadius() {
        return mRadius.getValue();
    }
}
