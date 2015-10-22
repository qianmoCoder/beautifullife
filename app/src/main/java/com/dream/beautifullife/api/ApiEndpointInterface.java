package com.dream.beautifullife.api;

import com.squareup.okhttp.RequestBody;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by admin on 2015/10/22.
 */
public interface ApiEndpointInterface<T> {

    @GET("/users/{username}")
    Call<T> getT(@Path("username") String username);

    @GET("/group/{id}/users")
    Call<List<T>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("/group/{id}/users")
    Call<List<T>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

    //@POST("https://api.github.com/api/v3")
    @POST("/users/new")
    Call<T> createUser(@Body T user);

    @Multipart
    @POST("/some/endpoint")
    Call<T> someEndpoint(@Part("name1") String name1, @Part("name2") String name2);

    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<T> updateUser(@Field("first_name") String name1, @Field("last_name") String name2);

    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<T> someEndpoint(@FieldMap Map<String, String> names);

    @POST("list")
    Observable<T> loadUsers();

    @Multipart
    @PUT("/user/photo")
    Call<T> updateUser(@Part("photo") RequestBody photo,@Part("description")RequestBody descriptioin);

//    @Multipart
//    @PUT("/user/photo")
//    Call<T> updateUser(@Part("photo") TypedFile photo,@Part("description")TypedString descriptioin);

    @Headers("Cache-Control:max-age=640000")
    @GET("/widget/list")
    List<T> widgetList();

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}")
    Call<T> getUser(@Path("username") String username);

    @GET("/user")
    Call<T> getUser1(@Header("Authorization") String authorization);
}
