package com.baize.fireeyekotlin.http.reqs

import com.baize.fireeyekotlin.http.BuildFactory
import com.baize.fireeyekotlin.http.service.ApiKaiyan
import com.baize.fireeyekotlin.bean.HomeBean
import rx.Observable

/**
 * Created by 彦泽 on 2018/8/15.
 */
class ReqoKaiyan : BaseReqo() {

    private object Holder {
        val INSTANCE = ReqoKaiyan()
    }

    companion object {
        val instance: ReqoKaiyan by lazy { Holder.INSTANCE }
    }

    fun getHomeData(): Observable<HomeBean> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN).getHomeData()) as Observable<HomeBean>)
    }

    fun getHomeMoreData(date: String, num: String): Observable<HomeBean> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN).getHomeMoreData(date, num)) as Observable<HomeBean>)
    }
}