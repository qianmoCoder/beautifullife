package com.ddu.icore.common

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by yzbzz on 2019-08-19.
 */
object Schedulers {

    fun <T> compose(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}