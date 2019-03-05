package com.ddu.sdk.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

/**
 * Created by yzbzz on 2017/12/19.
 */

class GlideImageLoader : IImageLoader {

    override fun <T> loadUrl(t: T, imageView: ImageView) {
        val context = imageView.context
        Glide.with(context).load(t).into(imageView)
        when (t) {
            is String -> {
                Glide.with(context).load(t).into(imageView)
            }
            is Int -> {
                Glide.with(context).load(t).into(imageView)
            }
            is Drawable -> {
                Glide.with(context).load(t).into(imageView)
            }
            is File -> {
                Glide.with(context).load(t).into(imageView)
            }
            is Bitmap -> {
                Glide.with(context).load(t).into(imageView)
            }
            is Uri -> {
                Glide.with(context).load(t).into(imageView)
            }
            is Array<*> -> {
                when {
                    t.isArrayOf<Byte>() -> Glide.with(context).load(t).into(imageView)
                    else -> throw Exception("Has wrong type ${t.javaClass.name}")
                }
            }
            else -> {
                throw Exception("Has wrong type $t")
            }
        }
    }
}
