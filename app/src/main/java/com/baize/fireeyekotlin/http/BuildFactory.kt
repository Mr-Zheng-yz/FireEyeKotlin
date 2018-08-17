package com.baize.fireeyekotlin.http

import com.baize.fireeyekotlin.http.service.ApiQsbk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by 彦泽 on 2018/8/15.
 */
class BuildFactory private constructor() {

    private var gsbkHttps: Any? = null//糗事百科api

    private object Holder {
        val INSTANCE = BuildFactory()
    }

    companion object {
        val instance: BuildFactory by lazy { Holder.INSTANCE }
    }

    fun <T> create(apiService: Class<T>, url: String): T {
        when (url) {
            ApiQsbk.API_QSBK -> {
                if (gsbkHttps == null) {
                    synchronized(BuildFactory::class.java) {
                        if (gsbkHttps == null) {
                            gsbkHttps = getBuilder(url).build().create(apiService)
                        }
                    }
                }
                return gsbkHttps as T
            }
        }
        return gsbkHttps as T
    }

    //==================================创建Retrofit.Builder（创建拦截器等）==============================
    fun getBuilder(url: String): Retrofit.Builder {
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.client(getOkClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        return builder
    }

    fun getOkClient(): OkHttpClient {
        var client: OkHttpClient
        client = getUnsafeOkHttpClient()
        return client
    }

    //获取的是不安全Client（信任所有证书）
    fun getUnsafeOkHttpClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
        okBuilder.addInterceptor(NormalIntercept())
        okBuilder.readTimeout(30, TimeUnit.SECONDS)
        okBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okBuilder.writeTimeout(30, TimeUnit.SECONDS)
        return okBuilder.build()
    }

    //没有特殊操作的拦截器
    class NormalIntercept : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain?.request()

            return chain?.proceed(request)!!
        }
    }
}
