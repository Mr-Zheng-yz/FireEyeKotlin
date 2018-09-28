package com.baize.fireeyekotlin.mvvm.model

import android.arch.lifecycle.MutableLiveData
import com.baize.fireeyekotlin.bean.SearchBean
import com.baize.fireeyekotlin.http.DefaultSubscriber
import com.baize.fireeyekotlin.http.reqs.ReqoKaiyan

/**
 * Created by 彦泽 on 2018/9/28.
 */
class SearchReopsitory {
    fun getSearchData(query: String, start: Int): MutableLiveData<SearchBean> {
        val data: MutableLiveData<SearchBean> = MutableLiveData()
        ReqoKaiyan.instance.getSearchData(query, start)
                .subscribe(object : DefaultSubscriber<SearchBean>() {
                    override fun _onError(errMsg: String) {
                        data.value = null
                    }

                    override fun _onNext(entity: SearchBean) {
                        data.value = entity
                    }
                })
        return data
    }
}