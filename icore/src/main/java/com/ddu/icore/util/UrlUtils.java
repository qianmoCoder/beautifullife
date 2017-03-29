package com.ddu.icore.util;

import java.util.regex.Pattern;

/**
 * Created by liuhongzhe on 16/5/16.
 */
public class UrlUtils {


    public static final String EXTRA_DISABLE_URL_OVERRIDE = "disable_url_override";

    public static final Pattern ACCEPTED_URI_SCHEMA = Pattern.compile(
            "(?i)" + // switch on case insensitive matching
                    "(" +    // begin group for schema
                    "(?:http|https|file):\\/\\/" +
                    "|(?:inline|data|about|javascript):" +
                    "|(?:.*:.*@)" +
                    ")" +
                    "(.*)");
}
