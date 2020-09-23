package com.ddu.ui.fragment.study.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_study_ui_drawable.*

/**
 * Created by yzbzz on 2017/5/19.
 */
@IElement("UI")
class DrawableFragment : DefaultFragment() {
    override fun initData(savedInstanceState: Bundle?) {}
    override fun getLayoutId(): Int {
        return R.layout.fragment_study_ui_drawable
    }

    var switchB = false
    override fun initView() {
        cv_content.setOnClickListener {
            dtv_left.visibility = if (switchB) {
                View.VISIBLE
            } else {
                View.GONE
            }
            if (switchB) {
                Glide.with(this).asBitmap().load(R.drawable.test_gif).into(iv_1)
                Glide.with(this).asGif().load(R.drawable.test_gif).into(iv_2)
            } else {
                Glide.with(this).asGif().load(R.drawable.test_gif).into(iv_1)
                Glide.with(this).asBitmap().load(R.drawable.test_gif).into(iv_2)
            }
            switchB = !switchB
        }

//        Glide.with(this).asGif().load(R.drawable.egg_icon).into(iv_1)
        Glide.with(this).asBitmap().load(R.drawable.test_gif).into(iv_2)

        Glide.with(mContext).asGif().load(R.drawable.egg_icon).addListener(object : RequestListener<GifDrawable?> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<GifDrawable?>, isFirstResource: Boolean): Boolean {
                Log.v("lhz","onLoadFailed")
                iv_1.post {
                    Glide.with(mContext).asBitmap().load("1234").into(iv_1)
                }
                return false
            }

            override fun onResourceReady(resource: GifDrawable?, model: Any, target: Target<GifDrawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                return false
            }
        }).into(iv_1)
    }
}