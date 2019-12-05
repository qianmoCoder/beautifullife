package com.ddu.ui.activity

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.util.NavigatorUtils

/**
 * Created by yzbzz on 2018/1/17.
 */
class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFormat(PixelFormat.TRANSLUCENT)

        if (NavigatorUtils.navigatorToNative(this, intent)) {
            finish()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

}