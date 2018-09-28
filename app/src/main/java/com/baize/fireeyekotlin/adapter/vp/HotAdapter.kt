package com.baize.fireeyekotlin.adapter.vp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by 彦泽 on 2018/9/27.
 */
class HotAdapter(fm: FragmentManager, list: ArrayList<Fragment>, titles: MutableList<String>) : FragmentPagerAdapter(fm) {
    var mFm : FragmentManager = fm!!
    var mList : ArrayList<Fragment> = list
    var mTitles : MutableList<String> = titles
    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}