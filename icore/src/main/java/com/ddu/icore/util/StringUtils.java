package com.ddu.icore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuhongzhe on 16/6/7.
 */
public class StringUtils {

    public static String formatMoney(double d) {
        return String.format("%.2f", d);
    }

    public static boolean isChineseChar(String text) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        Pattern p = Pattern.compile("\\-?[0-9]+(.[0-9]+)?");
        return p.matcher(str).matches();
    }

    public static boolean isInteger(String text) {
        if (text == null) {
            return false;
        }
        Pattern p = Pattern.compile("\\-?[0-9]+");
        return p.matcher(text).matches();
    }
}
