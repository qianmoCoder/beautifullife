package com.ddu.ui.fragment

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.life.FoodFragment
import com.ddu.ui.fragment.life.IncomeTaxFragment
import com.ddu.ui.fragment.life.MortgageFragment
import kotlinx.android.synthetic.main.fragment_life.*

/**
 * Created by yzbzz on 2018/1/17.
 */
class LifeFragment : DefaultFragment(), View.OnClickListener {

    companion object {
        fun newInstance(): LifeFragment {
            val fragment = LifeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life
    }

    override fun initView() {
        setTitle(R.string.main_tab_life)
        oiv_calculator_house.setOnClickListener(this)
        oiv_calculator_income.setOnClickListener(this)

        oiv_food.setOnClickListener(this)

        oiv_joke.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.oiv_calculator_house -> startFragment(MortgageFragment::class.java)
            R.id.oiv_calculator_income -> startFragment(IncomeTaxFragment::class.java)
            R.id.oiv_food -> startFragment(FoodFragment::class.java)
            R.id.oiv_joke -> {
                val bundle = Bundle()
                bundle.putString("title", "测试")
                bundle.putString("url", "http://www.wdxhb.com/m/Icorejsapi.html")
                startFragment(WebFragment::class.java, bundle)
            }
        }
    }
}