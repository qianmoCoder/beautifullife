package com.ddu.ui.fragment.study.ui

import android.graphics.BitmapFactory
import com.ddu.R
import com.ddu.icore.common.ext.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.util.ZXingUtils
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_qr_code.*

/**
 * Created by yzbzz on 2018/6/13.
 */
@IElement("UI")
class QRFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_qr_code
    }

    override fun initView() {
//        val url = "weixin://wxpay/bizpayurl?pr=miuBAtm"
        val url = "\$0COHpuOML/zHLLmsCctBAjDcAdPgFLMwjIptE58y0NhovU0esSw6QdqH/At9siwMXTDsvORoKo/Hv25+KnTlERrvFWo8h+m+r4s2mVs6NGc=\$2"
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.refreshing_image_frame_01)
        btn_create_1.setOnClickListener {
            iv_qr_code.post {
                //                val bitmap = ZXingUtils.encodeBitmap(url, iv_qr_code.width, iv_qr_code.height, logoBitmap = null)
                val bitmap = ZXingUtils.createQRImage(ctx, url)
                iv_qr_code.setImageBitmap(bitmap)
            }
        }

        btn_create_2.setOnClickListener {
            iv_qr_code_1.setImageBitmap(ZXingUtils.createImage(url, logo = bitmap))
        }
    }
}
