package com.baize.fireeyekotlin.mvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.baize.fireeyekotlin.bean.HotBean
import com.baize.fireeyekotlin.mvvm.model.HotReopsitory

/**
 * Created by 彦泽 on 2018/9/27.
 */
class HotViewModel : ViewModel() {
    private var hotBean: MutableLiveData<HotBean>? = null
    private var hotRepository: HotReopsitory = HotReopsitory()

    fun getHotData(strategy: String): MutableLiveData<HotBean> {
        if (hotBean == null
                || hotBean?.value == null) {
            hotBean = MutableLiveData()
            return loadHot(strategy)
        } else {
            return hotBean as MutableLiveData<HotBean>
        }
    }

    private fun loadHot(strategy: String): MutableLiveData<HotBean> {
        var hotBean = hotRepository.getHotData(strategy)
        setHotBean(hotBean)
        return hotBean
    }

    private fun setHotBean(hotbean: MutableLiveData<HotBean>) {
        this.hotBean = hotbean
    }
}