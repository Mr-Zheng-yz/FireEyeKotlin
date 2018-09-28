package com.baize.fireeyekotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.adapter.RankAdapter
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.bean.FindBean
import com.baize.fireeyekotlin.mvvm.viewmodel.RankViewModel
import com.baize.fireeyekotlin.utils.showToast
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * Created by 彦泽 on 2018/9/25.
 */
class RankFragment : BaseFragment() {
    private var mIsFirst = true
    private var mPrepare = false
    private lateinit var mViewModel: RankViewModel
    private lateinit var mAdapter: RankAdapter

    override fun setContent(): Int {
        return R.layout.fragment_rank
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        mViewModel = ViewModelProviders.of(this).get(RankViewModel::class.java)
        mPrepare = true
        loadData()
    }

    private fun initView() {
        mAdapter = RankAdapter(context)
        rv_rank.layoutManager = GridLayoutManager(context, 2)
        rv_rank.adapter = mAdapter
    }

    override fun loadData() {
        if (!mPrepare or !mIsFirst or isHidden) {
            return
        }
        requestNet()
    }

    private fun requestNet() {
        mViewModel.getRankData().observe(this, Observer<MutableList<FindBean>> { t ->
            if (t != null) {
                mAdapter.addAll(t)
                mAdapter.notifyDataSetChanged()
                mIsFirst = false
                showContentView()
            } else {
                context?.showToast(message = "加载错误...")
                showError()
            }
        })
    }

    override fun onRefresh() {
        requestNet()
    }
}