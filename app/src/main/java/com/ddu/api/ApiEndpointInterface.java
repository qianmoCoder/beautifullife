package com.ddu.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


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
