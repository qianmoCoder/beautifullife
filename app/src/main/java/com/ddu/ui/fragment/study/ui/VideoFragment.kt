package com.ddu.ui.fragment.study.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.common.ext.alphaAnimator
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.util.ToastUtils
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_video.*

/**
 * Created by yzbzz on 2017/6/22.
 */
@IElement("UI")
class VideoFragment : DefaultFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_video
    }

    override fun initView() {
        btn_ok.setOnClickListener { ToastUtils.showToast("hello") }
        surface_view.setVideoURI(Uri.parse("android.resource://" + context?.packageName +
                "/" + R.raw.videoviewdemo))
        //        surface_view.setMediaController(new MediaController(mContext));
        surface_view.requestFocus()
        surface_view.setOnPreparedListener { mp ->
            mp.setOnBufferingUpdateListener { mp, percent ->
                val currentPosition = surface_view.currentPosition
                val duration = surface_view.duration
            }
            surface_view.start()
            mp.isLooping = true
        }
        surface_view.setOnCompletionListener {
            surface_view.start()
        }

        BaseApp.postDelayed(Runnable {
            if (!isDetached) {
                ll_other.visibility = View.VISIBLE
                ll_other.alphaAnimator(1000, 0f, 1f).start()
            }
        }, 5000)

    }

    override fun initData(savedInstanceState: Bundle?) {}

    //    private String stringForTime(int timeMs) {
    //        int totalSeconds = timeMs / 1000;
    //
    //        int seconds = totalSeconds % 60;
    //        int minutes = (totalSeconds / 60) % 60;
    //        int hours   = totalSeconds / 3600;
    //
    //        mFormatBuilder.setLength(0);
    //        if (hours > 0) {
    //            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
    //        } else {
    //            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
    //        }
    //    }

    override fun isShowTitleBar(): Boolean {
        return false
    }
}
