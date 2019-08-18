package com.ddu.app;

import android.content.Context;
import android.util.Log;
import androidx.annotation.Keep;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by yzbzz on 2019-08-18.
 */
public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(App.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("27756981", "1cb4812580a3271e40b8ab53c4f099b7", rsa)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Log.v("lhz", "mode: " + mode + " code: " + code + " inofo: " + info + " handlePatchVersion: " + handlePatchVersion);
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.v("lhz", "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            instance.killProcessSafely();
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.v("lhz", "sophix preload patch success. restart app to make effect.");
                        }
                    }
                }).initialize();
    }

    private String rsa = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+vTny+lLjhT1rONv/Q3mMMLWmxLpk7dnSKicstQFDw+5PgU2AdWpkgMS5n0/8jB48UTmohLYNCKrV+3lq1ruF+GL5l9sk8xeqhVzMf5B5Y+PwFm+DAkiXdbWJg+shMsKW8XK+4SCvZgeQ62igxkq1e8XwWVs6iWWVExoERIRsgu0pJofOTiqzhBvyDAVOeIGia9P0aveM2BQMiu4tnIMtZR5K3YHLAdYlyU1w9Zbe0ucC/IP0a5kn4khJuT/auzD1qrmYbKaERUt4sxZ7h5MN92l5pZ4vQTQv/FgnEznuYBbd5t+S+sYAQ44yFFZjsFvTg4qw8d1JNBh12ZRhtzkDAgMBAAECggEBAJoyNYfFcH74dveYZtmMvdVkh2WDgsAXPNFXPIY66gPTgHOLr5Da2yDWrLL3JN2glMzkST1/1ADjceA9RSHm6IBclf77EbFkSEyVNbkooy5PX7T3Hj0F+pNfGFxMZihXddI67S18qETDsfgpJ1NaGoluv00Xn9XJv3Jtl8m9bmSdZs2MTAz9kz7L7t9Mux1LmEg71/hXMBv8L89s8cGpNwNFlSSWnKYyFK94dnE+K9lMseRqnVZg49zwoWSzzw65RTmf92ctEoXYcCMn2CjNRzRjf7FK8Vyk4uHKYNF+yclf3LmMsw8orSBcwWvePsmP85TMJZy/86EIZmlT3FYpQHkCgYEA/dlSWhRSNc7UiAWyp7hUC6YeAVM+qEC8T4KBmzGJRPfhSbpChjFtZ3CiIxkoSU6PyNfTwXF3qAox4nAsadJS4Ru9QhX/th6UvBrD6oaQd0zWMu28hhjhYuJAwRj5YZ9Ho2QkKbZBR+TMp6RWhTGFAVwAdMHo28d5fVW/RD1+LmcCgYEAwFr/7wB/jEpC+ifoxSiZbx7Hded1GvUEOQduSAwPLlG/9O2hd1J6uipCOrq8xwG6QLTlP9jFg/4hH/0AP2ZkPmJnfzJFdrXlGjgOkqWGwHE/9ojERso3mVZJ8fak1XPYDjr7ct5JXJaLMN2YbLKhzqbO5sAiQrB/ODFxd9D4hwUCgYEA3Ie7UoSK8zIPU2hXz/uEEGatdItESNmMfyqqvA+p6F/Mucd12a0BFbc1NHa7mAwHP/24ejrAMB1DPywieYB9/hCiN9Jyx14D6vL5tzpWMT5wa+g7OuhMjiJvDRJMPLIqg1kRTaeHSRUNcqqDe80MdqTdxJFdYeoGsosW0gr2HwsCgYA3OqmbdPgqTrQ2YTkbStHrLto0RzW/fLQur6yBcTz2nrBP4d7nhL4KL9H+TNmhBiT1KTtdfp8Pi+r05U6p5XtI0jZHNibts8JJKek9P9uV665i2dgJP0yBcV4f5CJlyzIwu8ywqZREylDCwg1ewFdnH0vuwyHTJhwRBVk//rctBQKBgQCH7rEQzZSJVxj4jw56oJfVsoUUDwt+rC/JqBONHlAOxkjTfF/wkI1X2ysX0YkUJIg2xrvfZO1g72wobchKhfr4pH1DJm1jbbrFS+5gkqv3NxzEHLdUBq9XdAFtnjuGlAF7I+OkE/H7XBEPhYD9z4gKs6dNCMQkUYBI/c8CdXxXKQ==";
}
