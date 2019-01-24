package com.ddu.ui.fragment.study.imitate

import android.os.Bundle
import com.ddu.R
import com.ddu.icore.common.act
import com.ddu.icore.common.ctx
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.model.AddressBean
import com.ddu.ui.dialog.AddressPickerDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iannotation.IElement
import kotlinx.android.synthetic.main.fragment_ui_jd.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by lhz on 16/5/6.
 */
@IElement("HI")
class JDFragment : DefaultFragment() {

    var addressBeans: List<AddressBean>? = null

    var areaPickerView: AddressPickerDialog? = null
    private var values: IntArray = intArrayOf()

    override fun initData(savedInstanceState: Bundle?) {
        addressBeans = Gson().fromJson<List<AddressBean>>(getCityJson(), object : TypeToken<List<AddressBean>>() {
        }.type)
        areaPickerView = AddressPickerDialog(ctx,R.style.Dialog,addressBeans)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_ui_jd
    }

    override fun initView() {

        areaPickerView?.setAreaPickerViewCallback(object : AddressPickerDialog.AreaPickerViewCallback {
            override fun callback(vararg value: Int) {
                values = value
                if (value.size == 3) {
                    addressBeans?.let {
                        btn_select.text = "${it.get(value[0])?.label}-${it.get(value[0])?.children[value[1]]?.label}-${it.get(value[0]).children[value[1]].children[value[2]].label}"
                    }
                } else {
                    addressBeans?.let {
                        btn_select.text = "${it.get(value[0])?.label}${it.get(value[0])?.children[value[1]]?.label}"
                    }
                }

            }
        })

        btn_select.setOnClickListener {
            areaPickerView?.setSelect(*values)
            areaPickerView?.show()
        }
    }

    private fun getCityJson(): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = act.assets
            val bf = BufferedReader(InputStreamReader(
                    assetManager.open("region.json")))
            bf.use { r ->
                r.lineSequence().forEach {
                    stringBuilder.append(it)
                }
            }
//            var line: String? = null
//            while ((line = bf.readLine()) != null) {
//                stringBuilder.append(line)
//            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}
