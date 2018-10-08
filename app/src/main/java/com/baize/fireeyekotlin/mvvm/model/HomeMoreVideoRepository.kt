package com.baize.fireeyekotlin.mvvm.model

import android.arch.lifecycle.MutableLiveData
import com.baize.fireeyekotlin.bean.HomeBean
import com.baize.fireeyekotlin.http.DefaultSubscriber
import com.baize.fireeyekotlin.http.reqs.ReqoKaiyan

/**
 * Created by 彦泽 on 2018/9/28.
 */
class HomeMoreVideoRepository {
    fun getHomeMoreData(date: String): MutableLiveData<HomeBean> {
        val data: MutableLiveData<HomeBean> = MutableLiveData()
        ReqoKaiyan.instance.getHomeMoreData(date, "2")
                .subscribe(object : DefaultSubscriber<HomeBean>() {
                    override fun _onError(errMsg: String) {
                        data.value = null
                    }

                    override fun _onNext(entity: HomeBean) {
                        data.value = entity
                    }
                })
        return data
    }
}