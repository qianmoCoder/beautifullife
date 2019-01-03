package com.ddu.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ddu.R
import kotlinx.android.synthetic.main.activity_scrolling2.*

class ScrollingActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling2)
        setSupportActionBar(toolbar)
    }
}
