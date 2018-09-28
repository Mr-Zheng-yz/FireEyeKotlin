package com.baize.fireeyekotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.adapter.FindAdapter
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.bean.HotBean
import com.baize.fireeyekotlin.mvvm.viewmodel.HotViewModel
import com.baize.fireeyekotlin.utils.showToast
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * Created by 彦泽 on 2018/9/27.
 */
class FindFragment : BaseFragment() {
    private var mIsFirst = true
    private var mIsPrepare = false

    private lateinit var mAdapter: FindAdapter
    private lateinit var mViewModel: HotViewModel

    lateinit var mStrategy: String

    override fun setContent(): Int {
        return R.layout.fragment_find
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        mViewModel = ViewModelProviders.of(this).get(HotViewModel::class.java)
        mIsPrepare = true
        loadData()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = FindAdapter(context)
        recyclerView.adapter = mAdapter
        if (arguments != null) {
            mStrategy = arguments!!.getString("strategy")
        }
    }

    override fun loadData() {
        if (!mIsFirst || !mIsVisible || !mIsPrepare) {
            return
        }
        requestDate()
    }

    fun requestDate() {
        mViewModel.getHotData(mStrategy).observe(this,Observer<HotBean>{ t ->
            if (t != null) {
                mAdapter.data.clear()
                t.itemList?.forEach{
                    it.data?.let { it1 -> mAdapter.add(it1) }
                }
                mAdapter.notifyDataSetChanged()
                mIsFirst = false
                showContentView()
            }else{
                context?.showToast(message = "加载错误...")
                showError()
            }
        })
    }
}