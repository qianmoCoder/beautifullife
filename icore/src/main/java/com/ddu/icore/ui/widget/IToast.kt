package com.ddu.icore.ui.widget

import android.graphics.Color
import androidx.annotation.ColorInt
import android.widget.Toast
import com.ddu.icore.ICore

/**
 * Created by yzbzz on 2018/8/21.
 */
class IToast private constructor() {



    val mToast by lazy {
        Toast(ICore.context)
    }

//    lateinit var mView
//    lateinit var mTextView
//    lateinit var mImageView

    init {
//        mView = LayoutInflater.from(ICore.getContext()).inflate(R.layout.i_default_toast, null)
    }

    object Config {
        @ColorInt
        private var DEFAULT_TEXT_COLOR = IToast.DEFAULT_TEXT_COLOR
        @ColorInt
        private var ERROR_COLOR = IToast.ERROR_COLOR
        @ColorInt
        private var INFO_COLOR = IToast.INFO_COLOR
        @ColorInt
        private var SUCCESS_COLOR = IToast.SUCCESS_COLOR
        @ColorInt
        private var WARNING_COLOR = IToast.WARNING_COLOR

        fun reset() {
            IToast.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
            IToast.ERROR_COLOR = Color.parseColor("#D50000")
            IToast.INFO_COLOR = Color.parseColor("#3F51B5")
            IToast.SUCCESS_COLOR = Color.parseColor("#388E3C")
            IToast.WARNING_COLOR = Color.parseColor("#FFA900")

        }
    }

    private object SingletonHolder {
        val instance = IToast()
    }

    companion object {
        val instance = SingletonHolder.instance

        @ColorInt
        private var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
        @ColorInt
        private var ERROR_COLOR = Color.parseColor("#D50000")
        @ColorInt
        private var INFO_COLOR = Color.parseColor("#3F51B5")
        @ColorInt
        private var SUCCESS_COLOR = Color.parseColor("#388E3C")
        @ColorInt
        private var WARNING_COLOR = Color.parseColor("#FFA900")
        @ColorInt
        private val NORMAL_COLOR = Color.parseColor("#353A3E")
    }

}