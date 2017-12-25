package com.ddu.config;

import com.ddu.config.url.UrlConfig;

/**
 * Created by yzbzz on 2017/12/21.
 */

public class ConfigManager {

    public static ConfigManager getInstance() {
        return ConfigManager.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static ConfigManager instance = new ConfigManager();
    }

    public static UrlConfig getUrlConfig() {
        return null;
    }
}
