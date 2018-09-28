package com.baize.fireeyekotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.adapter.ResultAdapter
import com.baize.fireeyekotlin.base.BaseActivity
import com.baize.fireeyekotlin.bean.SearchBean
import com.baize.fireeyekotlin.mvvm.viewmodel.SearchViewModel
import com.baize.fireeyekotlin.utils.CommonUtils
import com.baize.fireeyekotlin.utils.showToast
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity() {

    private lateinit var mKeyWord: String
    private lateinit var mAdapter: ResultAdapter
    private lateinit var mViewModel: SearchViewModel

    var mIsRefresh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        mKeyWord = intent.getStringExtra("keyWord")
        title = "\'$mKeyWord\'相关"
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        initView()
        loadDate()
    }

    private fun initView() {
        srl_search.scrollBarFadeDuration = 1000
        srl_search.setColorSchemeColors(CommonUtils.getColor(R.color.green)
                , CommonUtils.getColor(R.color.yellow)
                , CommonUtils.getColor(R.color.red)
                , CommonUtils.getColor(R.color.purple))
        srl_search.setOnRefreshListener {
            if (!mIsRefresh) {
                mIsRefresh = true
                mViewModel.setStart(10)
                loadDate()
            }
        }

        mAdapter = ResultAdapter(this)
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = mAdapter
        rv_search.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("yanze", "newState:$newState")
                var layoutManager: LinearLayoutManager = rv_search.layoutManager as LinearLayoutManager
                var lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mAdapter.data.size - 1) {
                    var start = mViewModel.getStart()
                    start += 10
                    mViewModel.setStart(start)
                    Log.e("yanze", "start:$start")
                    loadDate()
                }
            }
        })
    }

    //加载数据
    private fun loadDate() {
        mViewModel.getSearchData(mKeyWord).observe(this, Observer<SearchBean> { t ->
            if (t != null) {
                showContentView()
                if (mIsRefresh) {
                    mIsRefresh = false
                    srl_search.postDelayed({
                        srl_search.isRefreshing = false
                    }, 1000)
                    mAdapter.data.clear()
                }
                t.itemList.forEach {
                    it.data?.let { it1 -> mAdapter.add(it1) }
                }
                mAdapter.notifyDataSetChanged()
            } else {
                showToast(message = "加载错误...")
                showError()
            }
        })
    }

    override fun onRefresh() {
        loadDate()
    }
}
