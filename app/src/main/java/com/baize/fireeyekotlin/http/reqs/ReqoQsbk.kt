package com.baize.fireeyekotlin.http.reqs

import com.baize.fireeyekotlin.http.BuildFactory
import com.baize.fireeyekotlin.http.service.ApiQsbk
import com.baize.fireeyekotlin.mvvm.model.QsbkListBean
import rx.Observable


/**
 * Created by 彦泽 on 2018/8/15.
 */
class ReqoQsbk private constructor():BaseReqo(){

    private object Holder {
        val INSTANCE = ReqoQsbk()
    }

    companion object {
        val instance: ReqoQsbk by lazy { Holder.INSTANCE }
    }

    fun getQsbk(page:Int): Observable<QsbkListBean> {
        return (transform(BuildFactory.instance.create(ApiQsbk::class.java,ApiQsbk.API_QSBK).getQsbkList(page)) as Observable<QsbkListBean>)
    }

}