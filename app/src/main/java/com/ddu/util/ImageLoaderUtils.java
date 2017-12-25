package com.ddu.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by yzbzz on 2017/12/19.
 */

public class ImageLoaderUtils {

    public void loadUrl(Context context, String url, ImageView imageView) {
    }

    public void loadUrl(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }

    public void loadUrl(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url).into(imageView);
    }

    public RequestOptions getRequestOptions() {
        RequestOptions options = new RequestOptions();
        return options;
    }
}
