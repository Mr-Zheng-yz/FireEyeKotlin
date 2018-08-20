package com.baize.fireeyekotlin.http

import com.baize.fireeyekotlin.app.FireEyeApp
import com.baize.fireeyekotlin.http.service.ApiQsbk
import com.baize.fireeyekotlin.utils.CheckNetwork
import com.baize.fireeyekotlin.utils.DebugUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
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
        val httpCacheDirectory = File("${FireEyeApp.instance?.cacheDir}response")
        val cacheSize = 50 * 1024 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        val okBuilder = OkHttpClient.Builder()
        okBuilder.readTimeout(30, TimeUnit.SECONDS)
        okBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okBuilder.writeTimeout(30, TimeUnit.SECONDS)
        //添加缓存，无网络时会拿缓存，只会缓存get请求
        okBuilder.addInterceptor(HttpHeadInterceptor())
        okBuilder.addInterceptor(AddCacheInterceptor())
        okBuilder.cache(cache)
        okBuilder.addInterceptor(getInterceptor())
        return okBuilder.build()
    }

    //处理请求头
    class HttpHeadInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request: Request = chain!!.request()
            val builder: Request.Builder = request.newBuilder()
            builder.addHeader("Accept", "application/json;versions=1")
            if (CheckNetwork.isNetworkConnected(FireEyeApp.instance())) {
                val maxAge = 60
                builder.addHeader("Cache-Control", "public, max-age=$maxAge")
            } else {
                val maxStale = 60 * 60 * 24 * 28
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
            }
            return chain.proceed(builder.build())
        }

    }

    //缓存Get请求
    class AddCacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val cacheBuilder: CacheControl.Builder = CacheControl.Builder()
            cacheBuilder.maxAge(0, TimeUnit.SECONDS)
            cacheBuilder.maxStale(365, TimeUnit.DAYS)
            val cacheControl = cacheBuilder.build()
            var request: Request = chain!!.request()
            if (!CheckNetwork.isNetworkConnected(FireEyeApp.instance())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build()
            }
            val originalResponse: Response = chain.proceed(request)
            if (CheckNetwork.isNetworkConnected(FireEyeApp.instance())) {
                //read from cache
                val maxAge = 0
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=$maxAge")
                        .build()
            } else {
                // tolerate 4-weeks stale
                val maxStale = 60 * 60 * 24 * 28
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }
    }

    //日志拦截器
    fun getInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (DebugUtil.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)//测试
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)//打包
        }
        return interceptor
    }

}
