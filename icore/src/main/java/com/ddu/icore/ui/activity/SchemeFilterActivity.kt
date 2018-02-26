package com.ddu.icore.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ddu.icore.navigation.Navigator

/**
 * Created by yzbzz on 2018/2/12.
 */

class SchemeFilterActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.data
        Navigator.navigation(uri)
    }
}
