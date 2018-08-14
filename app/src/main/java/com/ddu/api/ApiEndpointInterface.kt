package com.ddu.api

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by admin on 2015/10/22.
 */
interface ApiEndpointInterface<T> {

    @GET("/users/{username}")
    fun getT(@Path("username") username: String): Call<T>

    @GET("/group/{id}/users")
    fun groupList(@Path("id") groupId: Int, @Query("sort") sort: String): Call<List<T>>

    @GET("/group/{id}/users")
    fun groupList(@Path("id") groupId: Int, @QueryMap options: Map<String, String>): Call<List<T>>

    //@POST("https://api.github.com/api/v3")
    @POST("/users/new")
    fun createUser(@Body user: T): Call<T>

    @Multipart
    @POST("/some/endpoint")
    fun someEndpoint(@Part("name1") name1: String, @Part("name2") name2: String): Call<T>

    @FormUrlEncoded
    @POST("/some/endpoint")
    fun updateUser(@Field("first_name") name1: String, @Field("last_name") name2: String): Call<T>

    @FormUrlEncoded
    @POST("/some/endpoint")
    fun someEndpoint(@FieldMap names: Map<String, String>): Call<T>

    @POST("list")
    fun loadUsers(): Observable<T>

    @Multipart
    @PUT("/user/photo")
    fun updateUser(@Part("photo") photo: RequestBody, @Part("description") descriptioin: RequestBody): Call<T>

    //    @Multipart
    //    @PUT("/user/photo")
    //    Call<T> updateUser(@Part("photo") TypedFile photo,@Part("description")TypedString descriptioin);

    @Headers("Cache-Control:max-age=640000")
    @GET("/widget/list")
    fun widgetList(): List<T>

    @Headers("Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-Sample-App")
    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<T>

    @GET("/user")
    fun getUser1(@Header("Authorization") authorization: String): Call<T>
}
