package com.ddu.sdk.network.retrofit;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yzbzz on 2017/12/22.
 */

public class MoreBaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl oldUrl = request.url();

        Request.Builder builder = request.newBuilder();
        List<String> urlNameList = request.headers("urlName");
        if (urlNameList != null && urlNameList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值  
            builder.removeHeader("urlname");
            //获取头信息中配置的value,如：manage或者mdffx  
            String urlname = urlNameList.get(0);
            HttpUrl baseURL = null;
            //根据头信息中配置的value,来匹配新的base_url地址  
            if ("manage".equals(urlname)) {
//                baseURL = HttpUrl.parse(Api.base_url);
            } else if ("mdffx".equals(urlname)) {
//                baseURL = HttpUrl.parse(Api.base_url_mdffx);
            }
            //重建新的HttpUrl，需要重新设置的url部分  
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https  
                    .host(baseURL.host())//主机地址  
                    .port(baseURL.port())//端口  
                    .build();
            //获取处理后的新newRequest  
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(request);
        }
    }
}
