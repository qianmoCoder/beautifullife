package com.ddu.ui.activity.phrase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ddu.R
import kotlinx.android.synthetic.main.fragment_style_phrase.*

class StylePhraseActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_one.setOnClickListener(this)
        btn_two.setOnClickListener(this)
        btn_multi.setOnClickListener(this)
        btn_custom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_one -> {
                startActivity(Intent(this, OneSeparatorActivity::class.java))
            }
            R.id.btn_two -> {
                startActivity(Intent(this, TwoSeparatorActivity::class.java))
            }
            R.id.btn_multi -> {
                startActivity(Intent(this, MultiSeparatorActivity::class.java))
            }
            R.id.btn_custom -> {
                startActivity(Intent(this, CustomSeparatorActivity::class.java))
            }
        }
    }
}
