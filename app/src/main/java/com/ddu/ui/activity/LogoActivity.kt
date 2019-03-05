package com.ddu.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.common.startActivity

/**
 * Created by yzbzz on 2018/1/17.
 */
class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        setContentView(R.layout.activity_logo)
        BaseApp.postDelayed(Runnable {
            startActivity<MainActivity>()
            finish()
        }, 1000)
    }
}