package com.baize.fireeyekotlin.mvvm.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.baize.fireeyekotlin.bean.SearchBean
import com.baize.fireeyekotlin.mvvm.model.SearchReopsitory

/**
 * Created by 彦泽 on 2018/9/28.
 */
class SearchViewModel : ViewModel() {
    private var searchData: MutableLiveData<SearchBean>? = null
    private var searchReopsitory = SearchReopsitory()
    private var start: Int = 10
    private lateinit var keyWord: String

    fun getStart(): Int {
        return this.start
    }

    fun setStart(start: Int) {
        this.start = start
    }

    fun getSearchData(keyWord: String): MutableLiveData<SearchBean> {
        this.keyWord = keyWord
//        if (searchData == null
//                || searchData?.value == null) {
            searchData = MutableLiveData()
            return loadSearch()
//        } else {
//            return searchData as MutableLiveData<SearchBean>
//        }
    }

    private fun loadSearch(): MutableLiveData<SearchBean> {
        val search = searchReopsitory.getSearchData(keyWord, start)
        setSearchBean(search)
        return search
    }

    private fun setSearchBean(searchBean: MutableLiveData<SearchBean>) {
        this.searchData = searchBean
    }

}