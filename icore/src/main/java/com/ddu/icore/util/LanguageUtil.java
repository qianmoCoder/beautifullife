package com.ddu.icore.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by yzbzz on 2019/11/30.
 */
public class LanguageUtil {

    private LanguageUtil() {

    }

    public static String getStringByLocale(Context context, int strId, String language) {
//        int stringId = context.getResources().getIdentifier(strId, "string", context.getPackageName());
        Resources resources = getResourcesByLocale(context.getResources(), new Locale(language));
        return resources.getString(strId);
    }

    public static Resources getResourcesByLocale(Resources resources, Locale locale) {
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        return new Resources(resources.getAssets(), resources.getDisplayMetrics(), configuration);
    }

}
