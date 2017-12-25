package com.ddu.sdk.image;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * Created by yzbzz on 2017/12/19.
 */

public interface IImageLoaderManager {

    void loadUrl(Context context, String url, ImageView imageView);

    void loadUrl(Activity activity, String url, ImageView imageView);

    void loadUrl(Fragment fragment, String url, ImageView imageView);
}
