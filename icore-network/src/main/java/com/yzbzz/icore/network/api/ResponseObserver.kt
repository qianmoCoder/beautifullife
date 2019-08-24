package com.yzbzz.icore.network.api

import android.content.Context
import com.ddu.icore.dialog.LoadingDialog
import com.yzbzz.icore.network.vo.Resource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by yzbzz on 2019-08-19.
 */
abstract class ResponseObserver<T>(ctx: Context, showLoading: Boolean = true) : Observer<Resource<T>> {

    private var disposable: Disposable? = null
    private var isShowLoading = showLoading
    private var mContext = ctx

    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (isShowLoading) {
            LoadingDialog.getInstance().show(mContext)
        }
    }

    override fun onNext(t: Resource<T>) {
        try {
            if (Resource.isSuccessful(t.code)) {
                onResponse(ApiResponse.create(t))
            } else {
                onResponse(ApiResponse.create(t.code, Exception(t.msg)))
            }
        } catch (e: Throwable) {
            onError(e)
        } finally {
            disposable?.let {
                if (!it.isDisposed) {
                    it.dispose()
                }
            }
        }



    }

    override fun onError(e: Throwable) {
        onResponse(ApiResponse.create(-1, e))
        disposable?.dispose()
    }


    override fun onComplete() {
        if (isShowLoading) {
            LoadingDialog.getInstance().dismiss()
        }
    }

    abstract fun onResponse(response: ApiResponse<T>)
}