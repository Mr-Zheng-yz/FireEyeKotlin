package com.baize.fireeyekotlin.mvvm.model

import android.arch.lifecycle.MutableLiveData
import com.baize.fireeyekotlin.bean.FindBean
import com.baize.fireeyekotlin.http.DefaultSubscriber
import com.baize.fireeyekotlin.http.reqs.ReqoKaiyan

/**
 * Created by 彦泽 on 2018/9/25.
 */
class RankRepository {
    fun getRankData(): MutableLiveData<MutableList<FindBean>> {
        val data: MutableLiveData<MutableList<FindBean>> = MutableLiveData<MutableList<FindBean>>()
        ReqoKaiyan.instance.getFindData()
                .subscribe(object : DefaultSubscriber<MutableList<FindBean>>() {
                    override fun _onError(errMsg: String) {
                        data.value = null
                    }

                    override fun _onNext(entity: MutableList<FindBean>) {
                        data.value = entity
                    }
                })
        return data
    }
}