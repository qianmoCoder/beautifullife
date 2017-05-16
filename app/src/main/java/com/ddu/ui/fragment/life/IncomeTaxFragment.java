package com.ddu.ui.fragment.life;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ddu.R;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.NumberUtils;
import com.ddu.icore.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ddu.R.id.et_income_average;

/**
 * Created by liuhongzhe on 16/6/7.
 */
public class IncomeTaxFragment extends DefaultFragment {

    @Nullable
    @BindView(R.id.btn_income_tax)
    Button btnIncomeTax;
    @Nullable
    @BindView(R.id.et_income_age)
    EditText etIncomeAge;
    @Nullable
    @BindView(R.id.et_income_tax)
    EditText etIncomeTax;
    @Nullable
    @BindView(et_income_average)
    EditText etIncomeAverage;
    @Nullable
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @Nullable
    @BindView(R.id.tv_insurance)
    TextView tvInsurance;
    @Nullable
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @Nullable
    @BindView(R.id.tv_total_deduction)
    TextView tvTotalDeduction;
    @Nullable
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @Nullable
    @BindView(R.id.tv_providentFund)
    TextView tvProvidentFund;
    @Nullable
    @BindView(R.id.tv_total_tax_before_deduction)
    TextView tvTotalTaxBeforeDeduction;
    @Nullable
    @BindView(R.id.tv_health_insurance_money)
    TextView tvHealthInsuranceMoney;

    private Unbinder unbinder;

    private double average = 7086;

    private double ceiling = average * 3;
    //    private double ceiling = 6463.0 * 3;
    private double calculatorMoney;

    @Override
    public void initData(Bundle savedInstanceState) {
        getActivity().getWindow().setBackgroundDrawable(null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_life_income_tax;
    }

    @Override
    public void initView() {
        unbinder = ButterKnife.bind(this, mView);
        etIncomeAverage.setText(average + "");

        setDefaultTitle("个税计算器");
    }

    @OnClick(R.id.btn_income_tax)
    public void onClick() {
//        AnimatorUtils.rotationX(btnIncomeTax, 2000, 0f, 180f, 0f).start();
        calculator();
        try {
//            URL url = new URL("http://behappy.Icore.com/app/");
//            intentToSms("18610289732", url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void intentToSms(String tel, String msg) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", msg);
        startActivity(intent);
    }

    private void calculator() {
        String averageMoney = etIncomeAverage.getText().toString().trim();
        if (!TextUtils.isEmpty(averageMoney)) {
            double money = Double.parseDouble(averageMoney);
            if (money > 0) {
                ceiling = money * 3;
            }
        }
        String moneyText = etIncomeTax.getText().toString();
        if (TextUtils.isEmpty(moneyText)) {
//            ToastUtil.showTextToast("请输入金额!");
            return;
        }
        double money = Double.parseDouble(moneyText);
        calculatorMoney = money;
        if (money > ceiling) {
            calculatorMoney = ceiling;
        }
        double insurance = (calculatorMoney * 8 / 100) + (calculatorMoney * 2 / 100 + 3);
        double providentFund = NumberUtils.parseDecimals(0, calculatorMoney * 12 / 100);
        double tax = getCalculatorTax(money - insurance - providentFund);

        tvTotalMoney.setText(StringUtils.formatMoney(money));
        tvInsurance.setText(StringUtils.formatMoney(insurance));
        tvProvidentFund.setText(StringUtils.formatMoney(providentFund));
        tvTotalTaxBeforeDeduction.setText(StringUtils.formatMoney(insurance + providentFund));
        tvTax.setText(StringUtils.formatMoney(tax));
        tvTotalDeduction.setText(StringUtils.formatMoney(insurance + providentFund + tax));
        tvMoney.setText(StringUtils.formatMoney(money - insurance - providentFund - tax));

        String ageText = etIncomeAge.getText().toString().trim();
        double age = NumberUtils.parseDecimals(0, ageText);
        String healthInsuranceMoney = StringUtils.formatMoney(getCalculatorHealthInsuranceMoney(calculatorMoney, age));
        tvHealthInsuranceMoney.setText(healthInsuranceMoney);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 35周岁以下的按本人缴费工资基数的0.8%划入
     * 35周岁至45周岁的按本人缴费工资基数的1%划入
     * 45周岁至退休的按本人缴费工资基数的2%划入
     * 70岁以上退休人员个人账户按每人每月110元划入
     * 70岁以下退休人员个人账户按每人每月100元划入。
     */
    private double getCalculatorHealthInsuranceMoney(double money, double age) {
        age = NumberUtils.parseDecimals(0, age);
        double proportionality;
        if (age < 35) {
            proportionality = money * 2 / 100 + money * 0.8 / 100;
        } else if (age >= 35 && age < 45) {
            proportionality = money * 2 / 100 + money * 1 / 100;
        } else if (age >= 45 && age < 65) {
            proportionality = money * 2 / 100 + money * 2 / 100;
        } else if (age >= 65 && age < 70) {
            proportionality = 100;
        } else {
            proportionality = 110;
        }
        return proportionality;
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
    private double getCalculatorTax(double money) {
        money = money - 3500.0;
        double result;
        if (money < 1500.0) {
            result = money * 3 / 100 - 0;
        } else if (money >= 1500 && money < 4500) {
            result = money * 10 / 100 - 105;
        } else if (money >= 4500 && money < 9000) {
            result = money * 20 / 100 - 555;
        } else if (money >= 9000 && money < 35000) {
            result = money * 25 / 100 - 1005;
        } else if (money >= 35000 && money < 55000) {
            result = money * 30 / 100 - 2755;
        } else if (money >= 55000 && money < 80000) {
            result = money * 35 / 100 - 5505;
        } else {
            result = money * 45 / 100 - 13505;
        }
        return result;
    }

}
