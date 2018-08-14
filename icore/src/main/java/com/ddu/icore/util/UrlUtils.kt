package com.ddu.icore.util

import java.util.regex.Pattern

/**
 * Created by liuhongzhe on 16/5/16.
 */
object UrlUtils {


    const val EXTRA_DISABLE_URL_OVERRIDE = "disable_url_override"

    val ACCEPTED_URI_SCHEMA = Pattern.compile(
            "(?i)" + // switch on case insensitive matching

                    "(" +    // begin group for schema

                    "(?:http|https|file):\\/\\/" +
                    "|(?:inline|data|about|javascript):" +
                    "|(?:.*:.*@)" +
                    ")" +
                    "(.*)")
}
