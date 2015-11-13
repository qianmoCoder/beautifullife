package com.beautifullife.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by admin on 2015/8/28.
 */
public class LanguageUtil {

    private static final String NO_LANGUAGE = "no_language";
    private static final String DEFAULT_LANGUAGE = "en";

    private static final String SYSTEM_LOCALE_LANGUAGE_STRING = "system_locale_language_string";
    private static final String SYSTEM_LOCALE_COUNTRY_STRING = "system_locale_country_string";

    private Locale[] locales = {Locale.SIMPLIFIED_CHINESE, Locale.TRADITIONAL_CHINESE, Locale.ENGLISH, Locale.FRANCE, Locale.GERMANY, Locale.JAPAN, Locale.KOREA, new Locale("ex"), new Locale("pt"), new Locale("ar")};

    private static final String ENAME[] = {"zh", "en", "fr", "de", "ja", "ko", "es", "pt", "ar"};

    public static Locale getSystemLocale(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("", 0);
        String language = sharedPreferences.getString(SYSTEM_LOCALE_LANGUAGE_STRING, NO_LANGUAGE);
        String country = sharedPreferences.getString(SYSTEM_LOCALE_COUNTRY_STRING, "");
        if (NO_LANGUAGE.equals(language)) {
            Locale locale = Locale.getDefault();
            String def = DEFAULT_LANGUAGE;
            for (int i = 0; i < ENAME.length; i++) {
                if (ENAME[i].equals(locale.getLanguage())) {
                    def = ENAME[i];
                    break;
                }
            }

            Locale cLocale = null;
            if ("zh".equals(def)) {
                if ("CN".equals(locale.getCountry())) {
                    cLocale = Locale.SIMPLIFIED_CHINESE;
                } else {
                    cLocale = Locale.TRADITIONAL_CHINESE;
                }
            } else {
                cLocale = new Locale(def);
            }
            setSystemLocale(context, cLocale);
            return cLocale;
        }
        return new Locale(language, country);
    }

    /**
     * 设置完成后，跳转到首页
     * @param context
     * @param locale
     */
    public static void setSystemLocale(Context context, Locale locale) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SYSTEM_LOCALE_LANGUAGE_STRING, locale.getLanguage());
        editor.putString(SYSTEM_LOCALE_COUNTRY_STRING, locale.getCountry());
        editor.commit();
    }

    /**
     * context --> getBaseContext();
     * 此方法需要在setContextView之前调用
     *
     * @param context
     */
    public static void reloadLanguageAction(Context context) {
        Locale locale = getSystemLocale(context);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, null);
        context.getResources().flushLayoutCache();
    }

}
