package com.ddu.icore.sdk

import com.ddu.icore.sdk.image.IImageLoader

/**
 * Created by yzbzz on 2019-08-10.
 */
class SDKManager private constructor() {

    private var mImageLoader: IImageLoader? = null

    fun setImageLoader(imageLoader: IImageLoader): SDKManager {
        this.mImageLoader = imageLoader
        return this
    }

    fun getImageLoader() = mImageLoader

    private object SingletonHolder {
        val instance = SDKManager()
    }

    companion object {
        val instance = SingletonHolder.instance

        fun get() = SingletonHolder.instance
    }
}
