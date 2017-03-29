package com.ddu.icore.util;

import java.math.BigDecimal;

public class NumberUtils {

    public static String parseDecimals(String money) {
        try {
            BigDecimal temp = new BigDecimal(money);
            temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
            return temp.toString();
        } catch (Exception e) {
            return money;
        }
    }

    public static double parseDecimals(int newScale, String decimals) {
        try {
            BigDecimal temp = new BigDecimal(Double.parseDouble(decimals));
            double result = temp.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static double parseDecimals(int newScale, double money) {
        try {
            BigDecimal temp = new BigDecimal(money);
            double result = temp.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        } catch (Exception e) {
            return money;
        }
    }

    public static double parseDecimals(double money) {
        try {
            BigDecimal temp = new BigDecimal(money);
            double result = temp.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return result;
        } catch (Exception e) {
            return money;
        }
    }
}
