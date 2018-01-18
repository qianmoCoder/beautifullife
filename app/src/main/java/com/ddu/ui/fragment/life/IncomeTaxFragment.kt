package com.ddu.ui.fragment.life

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.icore.util.NumberUtils
import com.ddu.icore.util.StringUtils
import kotlinx.android.synthetic.main.fragment_life_income_tax.*

/**
 * Created by liuhongzhe on 16/6/7.
 */
class IncomeTaxFragment : DefaultFragment() {

    private val average = 7706.0

    private var ceiling = average * 3
    //    private double ceiling = 6463.0 * 3;
    private var calculatorMoney: Double = 0.toDouble()

    override fun initData(savedInstanceState: Bundle?) {
        activity!!.window.setBackgroundDrawable(null)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_life_income_tax
    }

    override fun initView() {
        et_income_average.setText(average.toString() + "")
        btn_income_tax.setOnClickListener {
            calculator()
            try {
                //            URL url = new URL("http://behappy.Icore.com/app/");
                //            intentToSms("18610289732", url.toString());
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        setDefaultTitle("个税计算器")
    }

    private fun intentToSms(tel: String, msg: String) {
        val uri = Uri.parse("smsto:" + tel)
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", msg)
        startActivity(intent)
    }

    private fun calculator() {
        val averageMoney = et_income_average!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(averageMoney)) {
            val money = java.lang.Double.parseDouble(averageMoney)
            if (money > 0) {
                ceiling = money * 3
            }
        }
        val moneyText = et_income_tax!!.text.toString()
        if (TextUtils.isEmpty(moneyText)) {
            //            ToastUtil.showTextToast("请输入金额!");
            return
        }
        val money = java.lang.Double.parseDouble(moneyText)
        calculatorMoney = money
        if (money > ceiling) {
            calculatorMoney = ceiling
        }
        val insurance = calculatorMoney * 8 / 100 + (calculatorMoney * 2 / 100 + 3)
        val providentFund = NumberUtils.parseDecimals(0, calculatorMoney * 12 / 100)
        val tax = getCalculatorTax(money - insurance - providentFund)

        tv_total_money!!.text = StringUtils.formatMoney(money)
        tv_insurance!!.text = StringUtils.formatMoney(insurance)
        tv_providentFund!!.text = StringUtils.formatMoney(providentFund)
        tv_total_tax_before_deduction!!.text = StringUtils.formatMoney(insurance + providentFund)
        tv_tax!!.text = StringUtils.formatMoney(tax)
        tv_total_deduction!!.text = StringUtils.formatMoney(insurance + providentFund + tax)
        tv_money!!.text = StringUtils.formatMoney(money - insurance - providentFund - tax)

        val ageText = et_income_age!!.text.toString().trim { it <= ' ' }
        val age = NumberUtils.parseDecimals(0, ageText)
        val healthInsuranceMoney = StringUtils.formatMoney(getCalculatorHealthInsuranceMoney(calculatorMoney, age))
        tv_health_insurance_money!!.text = healthInsuranceMoney
    }

    /**
     * 35周岁以下的按本人缴费工资基数的0.8%划入
     * 35周岁至45周岁的按本人缴费工资基数的1%划入
     * 45周岁至退休的按本人缴费工资基数的2%划入
     * 70岁以上退休人员个人账户按每人每月110元划入
     * 70岁以下退休人员个人账户按每人每月100元划入。
     */
    private fun getCalculatorHealthInsuranceMoney(money: Double, age: Double): Double {
        var age = age
        age = NumberUtils.parseDecimals(0, age)
        val proportionality: Double
        if (age < 35) {
            proportionality = money * 2 / 100 + money * 0.8 / 100
        } else if (age >= 35 && age < 45) {
            proportionality = money * 2 / 100 + money * 1 / 100
        } else if (age >= 45 && age < 65) {
            proportionality = money * 2 / 100 + money * 2 / 100
        } else if (age >= 65 && age < 70) {
            proportionality = 100.0
        } else {
            proportionality = 110.0
        }
        return proportionality
    }

    /**
     * 1500 3% 0
     * 1500~4500 10% 105
     * 4500~9000 20% 555
     * 9000~35000 25% 1005
     * 35000~55000 30% 2755
     * 55000~80000 35% 5505
     * 80000 45% 13505
     *
     * @param money
     */
    private fun getCalculatorTax(money: Double): Double {
        var money = money
        money = money - 3500.0
        val result: Double
        if (money < 1500.0) {
            result = money * 3 / 100 - 0
        } else if (money >= 1500 && money < 4500) {
            result = money * 10 / 100 - 105
        } else if (money >= 4500 && money < 9000) {
            result = money * 20 / 100 - 555
        } else if (money >= 9000 && money < 35000) {
            result = money * 25 / 100 - 1005
        } else if (money >= 35000 && money < 55000) {
            result = money * 30 / 100 - 2755
        } else if (money >= 55000 && money < 80000) {
            result = money * 35 / 100 - 5505
        } else {
            result = money * 45 / 100 - 13505
        }
        return result
    }

}
