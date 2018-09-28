package com.baize.fireeyekotlin.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.adapter.vp.HotAdapter
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.ui.FindFragment
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * Created by 彦泽 on 2018/9/27.
 */
class HotFragment : BaseFragment() {
    var mTabs = listOf<String>("周排行", "月排行", "总排行").toMutableList()
    lateinit var mFragments: ArrayList<Fragment>
    val STRATEGY = arrayOf("weekly", "monthly", "historical")

    override fun setContent(): Int {
        return R.layout.fragment_hot
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        val weekFragment = FindFragment()
        val weekBundle = Bundle()
        weekBundle.putString("strategy", STRATEGY[0])
        weekFragment.arguments = weekBundle

        val monthFragment = FindFragment()
        val monthBundle = Bundle()
        monthBundle.putString("strategy", STRATEGY[1])
        monthFragment.arguments = monthBundle

        val allFragment = FindFragment()
        val allBundle = Bundle()
        allBundle.putString("strategy", STRATEGY[2])
        allFragment.arguments = allBundle

        mFragments = ArrayList()
        mFragments.add(weekFragment as Fragment)
        mFragments.add(monthFragment as Fragment)
        mFragments.add(allFragment as Fragment)
        vp_content.adapter = HotAdapter(childFragmentManager, mFragments, mTabs)
        vp_content.offscreenPageLimit = 3
        tabs.setupWithViewPager(vp_content)
        showContentView()
    }
}