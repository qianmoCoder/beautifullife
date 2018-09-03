package com.ddu.sdk

import com.ddu.sdk.image.GlideImageLoader
import com.ddu.sdk.image.IImageLoader

/**
 * Created by yzbzz on 2017/12/19.
 */

class SDKManager private constructor() {

    private var mImageLoader: IImageLoader = GlideImageLoader()

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
