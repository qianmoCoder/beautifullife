package com.ddu.ui.activity

import android.os.Bundle
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.ui.activity.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * Created by yzbzz on 2018/1/17.
 */
class LogoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        setContentView(R.layout.activity_logo)
        BaseApp.postDelayed(Runnable { startActivity<MainActivity>() }, 500)
    }
}