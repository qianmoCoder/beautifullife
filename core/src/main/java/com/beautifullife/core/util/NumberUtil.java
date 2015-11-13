package com.beautifullife.core.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2015/8/28.
 */
public class NumberUtil {

    private static final String REG_CHINESE = "^[\u4e00-\u9fa5]+$";
    private static final String REG_POST_CODE = "^[1-9]\\d{5}$";
    private static final String REG_ID_CARD = "^\\d{15}|\\d{18}$";
    private static final String REG_PHONE_NUMBER = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    private static final String REG_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    protected static boolean isMatch(String str,String regPattern){
        if(TextUtils.isEmpty(str)){
            return false;
        }

        Pattern p = Pattern.compile(regPattern );
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean isPhoneNum(String str){
        return isMatch(str, REG_PHONE_NUMBER);
    }

    public static boolean isEmail(String str){
        return isMatch(str, REG_EMAIL);
    }

    public static boolean isPostCode(String str){
        return isMatch(str,REG_POST_CODE);
    }

    public static boolean isIdCard(String str){
        return isMatch(str,REG_ID_CARD);
    }

    public static boolean isChinese(String str){
        return isMatch(str,REG_CHINESE);
    }


}
