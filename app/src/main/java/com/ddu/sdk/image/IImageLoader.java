package com.ddu.sdk.image;

import android.widget.ImageView;

/**
 * Created by yzbzz on 2017/12/19.
 */

public interface IImageLoader {

    <T> void loadUrl(T t, ImageView imageView);
}
