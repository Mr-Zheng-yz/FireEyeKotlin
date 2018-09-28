package com.baize.fireeyekotlin.mvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.baize.fireeyekotlin.bean.FindBean
import com.baize.fireeyekotlin.mvvm.model.RankRepository

/**
 * Created by 彦泽 on 2018/9/26.
 */
class RankViewModel : ViewModel() {

    private var rankBeans: MutableLiveData<MutableList<FindBean>>? = null
    private var rankRepository: RankRepository = RankRepository()

    fun getRankData(): MutableLiveData<MutableList<FindBean>> {
        if (rankBeans == null
                || rankBeans?.value == null) {
            rankBeans = MutableLiveData()
            return loadRank()
        } else {
            return rankBeans as MutableLiveData<MutableList<FindBean>>
        }
    }

    private fun loadRank(): MutableLiveData<MutableList<FindBean>> {
        var rankBean = rankRepository.getRankData()
        setRankBean(rankBeans)
        return rankBean
    }

    private fun setRankBean(rankBeans: MutableLiveData<MutableList<FindBean>>?) {
        this.rankBeans = rankBeans
    }
}