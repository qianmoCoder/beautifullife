package com.ddu.icore.api

import com.ddu.icore.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by yzbzz on 2019-08-18.
 */
class ApiClient private constructor() {

    private var mOkHttpClient: OkHttpClient? = null
    private var mRetrofit: Retrofit? = null

    private var mUrl = ""

    private var mConnectTimeout = 60L
    private var mReadTimeout = 60L
    private var mWriteTimeout = 60L

    fun setOkHttpClient(okHttpClient: OkHttpClient): ApiClient {
        this.mOkHttpClient = OkHttpClient()
        return this
    }

    fun setUrl(url: String): ApiClient {
        this.mUrl = url
        return this
    }

    fun setTimeout(
        connectTimeout: Long = TIME_OUT,
        readTimeout: Long = TIME_OUT,
        writeTimeout: Long = TIME_OUT
    ): ApiClient {
        this.mConnectTimeout = connectTimeout
        this.mReadTimeout = readTimeout
        this.mWriteTimeout = writeTimeout
        return this
    }

    private fun getRetrofit(url: String): Retrofit {
        if (null == mRetrofit) {
            mRetrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build()
        }
        return mRetrofit!!
    }

    private fun getOkHttpClient(): OkHttpClient {
        if (mOkHttpClient == null) {
            mOkHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(mConnectTimeout, TimeUnit.SECONDS)
                .readTimeout(mReadTimeout, TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeout, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG)
                            HttpLoggingInterceptor.Level.BODY
                        else
                            HttpLoggingInterceptor.Level.NONE
                    )
                )
                .build()
        }
        return mOkHttpClient!!
    }

    fun <T> create(serviceClass: Class<T>): T {
        return getRetrofit(mUrl).create(serviceClass)
    }

    fun <T> create(url: String, serviceClass: Class<T>): T {
        return getRetrofit(url).create(serviceClass)
    }

    private object SingletonHolder {
        val instance = ApiClient()
    }

    companion object {

        const val TIME_OUT = 60L

        @JvmStatic
        val instance = SingletonHolder.instance

        @JvmStatic
        fun get() = SingletonHolder.instance
    }
}