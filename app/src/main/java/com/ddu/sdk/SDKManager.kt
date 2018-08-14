package com.ddu.sdk

import com.ddu.sdk.image.GlideImageLoader
import com.ddu.sdk.image.IImageLoaderManager

/**
 * Created by yzbzz on 2017/12/19.
 */

class SDKManager private constructor() {

    private var mImageLoader: IImageLoaderManager? = null

    val imageLoader: IImageLoaderManager?
        get() {
            if (null == mImageLoader) {
                if (USE_GLIDE == DEFAULT_IMG_SDK) {
                    mImageLoader = GlideImageLoader()
                }
            }
            return mImageLoader
        }

    private object SingletonHolder {
        val instance = SDKManager()
    }

    companion object {

        private const val USE_GLIDE = 1
        private val DEFAULT_IMG_SDK = USE_GLIDE

        val instance = SingletonHolder.instance

        fun get() = SingletonHolder.instance
    }

}
