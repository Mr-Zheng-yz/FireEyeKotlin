package com.baize.fireeyekotlin.http.reqs

import com.baize.fireeyekotlin.bean.FindBean
import com.baize.fireeyekotlin.http.BuildFactory
import com.baize.fireeyekotlin.http.service.ApiKaiyan
import com.baize.fireeyekotlin.bean.HomeBean
import com.baize.fireeyekotlin.bean.HotBean
import com.baize.fireeyekotlin.bean.SearchBean
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

    fun getFindData(): Observable<MutableList<FindBean>> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN).getFindData()) as Observable<MutableList<FindBean>>)
    }

    fun getFindDetailData(categoryName: String, strategy: String = "date"): Observable<HotBean> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN)
                .getFindDetailData(categoryName, strategy, "26868b32e808498db32fd51fb422d00175e179df"
                        , 83)) as Observable<HotBean>)
    }

    fun getHotData(strategy: String): Observable<HotBean> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN)
                .getHotData(10, strategy, "26868b32e808498db32fd51fb422d00175e179df"
                        , 83)) as Observable<HotBean>)
    }

    fun getSearchData(query: String, start: Int): Observable<SearchBean> {
        return (transform(BuildFactory.instance.create(ApiKaiyan::class.java, ApiKaiyan.API_KAIYAN)
                .getSearchData(10, query, start)) as Observable<SearchBean>)
    }
}