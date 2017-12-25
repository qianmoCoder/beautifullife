package com.ddu.sdk;

import com.ddu.sdk.image.GlideImageLoader;
import com.ddu.sdk.image.IImageLoaderManager;

/**
 * Created by yzbzz on 2017/12/19.
 */

public class SDKManager {

    private static int USE_GLIDE = 1;
    private static int DEFAULT_IMG_SDK = USE_GLIDE;

    private IImageLoaderManager mImageLoader;

    public static SDKManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static SDKManager instance = new SDKManager();
    }

    public IImageLoaderManager getImageLoader() {
        if (null == mImageLoader) {
            if (USE_GLIDE == DEFAULT_IMG_SDK) {
                mImageLoader = new GlideImageLoader();
            }
        }
        return mImageLoader;
    }

}
