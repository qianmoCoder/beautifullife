package com.ddu.ui.dialog

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.dialog.AbsBottomDialogFragment
import kotlinx.android.synthetic.main.dialog_font_setting.*


/**
 * Created by yzbzz on 2017/10/31.
 */

class FontSettingDialog : AbsBottomDialogFragment() {

    override fun getLayoutId(): Int {
        return R.layout.dialog_bottom_content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        c.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                seekBar?.apply {
//                }
//            }
//
        doSomething()
//        object : DefaultRecycleViewAdapter<String>(mContext, mDatas) {
//
//            override fun getLayoutId(viewType: Int): Int {
//                return R.layout.rv_item_linear
//            }
//
//            override fun bindView(viewHolder: ViewHolder, data: String, position: Int) {
//                viewHolder.setText(R.id.tv_detail, data)
//            }
//        };
//        })
    }

    private fun doSomething() {
        val volumeSections = ArrayList<String>()
        volumeSections.add("静音")
        volumeSections.add("低")
        volumeSections.add("中")
        volumeSections.add("高")
        csk_font.initData(volumeSections)
        csk_font.setProgress(0)
        csk_font.setResponseOnTouch {

        }
    }
}
