package com.ddu.icore.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yzbzz on 2019/12/6.
 */
public class StringUtils {

    private StringUtils() {

    }

    public static String toLowerCase(String var0) {
        return var0.toLowerCase(Locale.US);
    }

    public static String toUpperCase(String var0) {
        return var0.toUpperCase(Locale.US);
    }

    public static int indexOfIgnoreCase(String var0, String var1) {
        return indexOfIgnoreCase(var0, var1, 0);
    }

    public static int indexOfIgnoreCase(String var0, String var1, int var2) {
        Matcher var3 = Pattern.compile(Pattern.quote(var1), Pattern.CASE_INSENSITIVE).matcher(var0);
        return var3.find(var2) ? var3.start() : -1;
    }

    public static String[] splitString(String var0, String var1) {
        StringTokenizer var2 = new StringTokenizer(var0, var1);
        String[] var3 = new String[var2.countTokens()];

        for(int var4 = 0; var4 < var3.length; ++var4) {
            var3[var4] = var2.nextToken();
        }

        return var3;
    }

    public static String trimWhitespace(String var0) {
        if (var0 == null) {
            return var0;
        } else {
            StringBuilder var1 = new StringBuilder();

            for(int var2 = 0; var2 < var0.length(); ++var2) {
                char var3 = var0.charAt(var2);
                if (var3 != '\n' && var3 != '\f' && var3 != '\r' && var3 != '\t') {
                    var1.append(var3);
                }
            }

            return var1.toString().trim();
        }
    }

    public static String join(Collection var0, String var1) {
        StringBuilder var2 = new StringBuilder();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
}
