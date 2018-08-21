package com.baize.fireeyekotlin.http.service

import com.baize.fireeyekotlin.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by 彦泽 on 2018/8/15.
 */
interface ApiKaiyan {
    companion object {
        val API_KAIYAN  = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据  ?date=1499043600000&num=2
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date :String, @Query("num") num :String) : Observable<HomeBean>

}