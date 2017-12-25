package com.ddu.config.url;

/**
 * Created by yzbzz on 2017/12/21.
 */

public abstract class UrlConfig implements IDnsConstants {

    public static String BUILD_TYPE = "qa";

    public static String BUILD_TYPE_QA = "qa";
    public static String BUILD_TYPE_PRE = "pre";
    public static String BUILD_TYPE_TEST = "test";
    public static String BUILD_TYPE_RELEASE = "release";

    @IDnsConfig(qa = "http://www.baidu.com",
            pre = "http://www.163.com",
            test = "http://www.xiaomi.com",
            release = "http://www.bbc.com")
    private String baseUrl;

    @IDnsConfig(release = "http://www.h5",
            test = "http://www.test.h5")
    private String h5Url;

}
