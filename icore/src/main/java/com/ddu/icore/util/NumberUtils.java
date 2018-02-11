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
}
