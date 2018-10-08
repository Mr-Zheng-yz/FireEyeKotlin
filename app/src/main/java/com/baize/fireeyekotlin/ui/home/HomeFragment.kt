package com.baize.fireeyekotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.adapter.HomeVideoAdapter
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.bean.HomeBean
import com.baize.fireeyekotlin.mvvm.viewmodel.HomeViewModel
import com.baize.fireeyekotlin.utils.showToast
import com.example.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

/**
 * Created by 彦泽 on 2018/8/14.
 */
class HomeFragment : BaseFragment() {
    private var mIsFirst = true
    private var mPrepare = false
    private lateinit var mAdapter: HomeVideoAdapter
    private var mViewModel: HomeViewModel? = null
    var data: String? = null

    override fun setContent(): Int {
        return R.layout.fragment_home
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mPrepare = true
        loadData()
    }

    private fun initView() {
        xrv_home.layoutManager = LinearLayoutManager(activity)
        xrv_home.setLoadingMoreEnabled(true)
        xrv_home.setPullRefreshEnabled(true)
        xrv_home.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                requestNet()
            }

            override fun onLoadMore() {
                moreData()
            }
        })

        mAdapter = HomeVideoAdapter(context)
        xrv_home.adapter = mAdapter
    }

    override fun loadData() {
        if (!mPrepare or !mIsFirst or isHidden) {
            return
        }
        requestNet()
    }

    private fun requestNet() {
        mViewModel?.homeVideo?.observe(this, object : Observer<HomeBean> {
            override fun onChanged(t: HomeBean?) {
                if (t != null) {
                    setData(t, true)
                } else {
                    context?.showToast("加载错误...")
                    showError()
                }
            }
        })
    }

    private fun moreData() {
        mViewModel?.getHomeMoreVideo(data)?.observe(this, object : Observer<HomeBean> {
            override fun onChanged(t: HomeBean?) {
                if (t != null) {
                    setData(t, false)
                }
            }
        })
    }

    private fun setData(homeBean: HomeBean, isFirst: Boolean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(homeBean.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()

        xrv_home.refreshComplete()
        if (isFirst) {//刷新
            mAdapter.data.clear()
        }

        homeBean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mAdapter.add(it) }
//        mAdapter.addAll(homeBean.issueList!![0].itemList.drop(1)) //去掉第一个
        mAdapter.notifyDataSetChanged()
        mIsFirst = false
        showContentView()
    }

    override fun onRefresh() {
        requestNet()
    }
}