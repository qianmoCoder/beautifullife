package com.ddu.sdk.network.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by yzbzz on 2017/12/22.
 */

public interface DemoService {

    @GET("user/book")
    Call<String> getBooks(@QueryMap Map<String, String> options);
}
