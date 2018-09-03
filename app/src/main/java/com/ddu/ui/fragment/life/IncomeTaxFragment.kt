package com.ddu.ui.fragment.life

import android.os.Bundle
import android.text.TextUtils
import com.ddu.R
import com.ddu.icore.common.formatMoney
import com.ddu.icore.common.parseDecimals
import com.ddu.icore.ui.fragment.DefaultFragment
import kotlinx.android.synthetic.main.fragment_life_income_tax.*

/**
 * Created by liuhongzhe on 16/6/7.
 */
class IncomeTaxFragment : DefaultFragment() {

    private val lineMoney = 5000

    private val average = 8467.0

    private var ceiling = average * 3

    private var calculatorMoney: Double = 0.toDouble()

    override fun initData(savedInstanceState: Bundle?) {
        activity?.window?.setBackgroundDrawable(null)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life_income_tax
    }

    override fun initView() {
        et_income_average.setText(average.toString())
        btn_income_tax.setOnClickListener {
            calculator()
        }
        setDefaultTitle("个税计算器")
    }


    private fun calculator() {
        val averageMoney = et_income_average.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(averageMoney)) {
            val money = java.lang.Double.parseDouble(averageMoney)
            if (money > 0) {
                ceiling = money * 3
            }
        }
        val moneyText = et_income_tax.text.toString()
        if (TextUtils.isEmpty(moneyText)) {
            return
        }
        val money = java.lang.Double.parseDouble(moneyText)
        calculatorMoney = money
        if (money > ceiling) {
            calculatorMoney = ceiling
        }
        val insurance = calculatorMoney * 8 / 100 + (calculatorMoney * 2 / 100 + 3)
        val providentFund = (calculatorMoney * 12 / 100).parseDecimals()
        val tax = getCalculatorTax(money - insurance - providentFund)

        tv_total_money.text = money.formatMoney()
        tv_insurance.text = insurance.formatMoney()
        tv_providentFund.text = providentFund.formatMoney()
        tv_total_tax_before_deduction.text = (insurance + providentFund).formatMoney()
        tv_tax.text = tax.formatMoney()
        tv_total_deduction.text = (insurance + providentFund + tax).formatMoney()
        tv_money.text = (money - insurance - providentFund - tax).formatMoney()

        val ageText = et_income_age.text.toString().trim { it <= ' ' }
        val age = ageText.toDouble()
        val healthInsuranceMoney = getCalculatorHealthInsuranceMoney(calculatorMoney, age).formatMoney()
        tv_health_insurance_money.text = healthInsuranceMoney
    }

    /**
     * 35周岁以下的按本人缴费工资基数的0.8%划入
     * 35周岁至45周岁的按本人缴费工资基数的1%划入
     * 45周岁至退休的按本人缴费工资基数的2%划入
     * 70岁以上退休人员个人账户按每人每月110元划入
     * 70岁以下退休人员个人账户按每人每月100元划入。
     */
    private fun getCalculatorHealthInsuranceMoney(money: Double, age: Double): Double {
        val selfProportion = 2
        var age = age.parseDecimals()
        return when {
            age < 35 -> money * (selfProportion + 0.8) / 100
            age < 45 -> money * (selfProportion + 1) / 100
            age < 65 -> money * (selfProportion + 2) / 100
            age < 70 -> 100.0
            else -> 110.0
        }
    }

    /**
     * 3000 3% 0
     * 3000~12000 10% 105
     * 12000~25000 20% 555
     * 25000~35000 25% 1005 20
     * 35000~55000 30% 2755
     * 55000~80000 35% 5505
     * 80000 45% 13505
     *
     * @param money
     */
    private fun getCalculatorTax(money: Double): Double {
        val result = money - lineMoney
        return when {
            result < 3000 -> result * 3 / 100 - 0
            result < 12000 -> result * 10 / 100 - 105
            result < 25000 -> result * 20 / 100 - 555
            result < 35000 -> result * 25 / 100 - 1005
            result < 55000 -> result * 30 / 100 - 2755
            result < 80000 -> result * 35 / 100 - 5505
            else -> money * 45 / 100 - 13505
        }
    }

}
