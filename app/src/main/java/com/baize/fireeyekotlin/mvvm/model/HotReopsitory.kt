package com.baize.fireeyekotlin.mvvm.model

import android.arch.lifecycle.MutableLiveData
import com.baize.fireeyekotlin.bean.HotBean
import com.baize.fireeyekotlin.http.DefaultSubscriber
import com.baize.fireeyekotlin.http.reqs.ReqoKaiyan

/**
 * Created by 彦泽 on 2018/9/27.
 */
class HotReopsitory {
    fun getHotData(strategy: String): MutableLiveData<HotBean> {
        val data: MutableLiveData<HotBean> = MutableLiveData()
        ReqoKaiyan.instance.getHotData(strategy)
                .subscribe(object : DefaultSubscriber<HotBean>() {
                    override fun _onError(errMsg: String) {
                        data.value = null
                    }

                    override fun _onNext(entity: HotBean) {
                        data.value = entity
                    }
                })
        return data
    }
}