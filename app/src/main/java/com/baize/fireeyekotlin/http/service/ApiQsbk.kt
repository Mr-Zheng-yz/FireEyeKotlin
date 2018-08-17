package com.baize.fireeyekotlin.http.service

import com.baize.fireeyekotlin.mvvm.model.QsbkListBean
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by 彦泽 on 2018/8/15.
 */
interface ApiQsbk {

    companion object {
        val API_QSBK = "http://m2.qiushibaike.com/"
    }

    /**
     * 糗事百科
     * @param page 页码，从1开始
     */
    @GET("article/list/text")
    fun getQsbkList(@Query("page") page: Int): Observable<QsbkListBean>
}