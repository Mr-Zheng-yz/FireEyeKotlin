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

/**
 * Created by 彦泽 on 2018/8/14.
 */
class HomeFragment : BaseFragment() {
    private var mIsFirst = true
    private var mPrepare = false
    private var mAdapter: HomeVideoAdapter? = null
    private var mViewModel: HomeViewModel? = null

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
        xrv_home.setLoadingMoreEnabled(false)
        xrv_home.setPullRefreshEnabled(false)
        xrv_home.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
            }

            override fun onLoadMore() {
            }
        })

        mAdapter = HomeVideoAdapter(context)
        xrv_home.adapter = mAdapter
    }

    override fun loadData() {
        if (!mPrepare or !mIsFirst or isHidden) {
            return
        }
        activity?.showToast("加载数据了")
        requestNet()
    }

    fun requestNet() {
        mViewModel?.homeVideo?.observe(this, object : Observer<HomeBean> {
            override fun onChanged(t: HomeBean?) {
                if (t != null) {
                    mAdapter?.addAll(t.issueList!![0].itemList.drop(1)) //去掉第一个
                    mAdapter?.notifyDataSetChanged()
                    mIsFirst = false
                    showContentView()
                } else {
                    context?.showToast("加载错误...")
                    showError()
                }
            }
        })
    }

    override fun onRefresh() {
        requestNet()
    }

}