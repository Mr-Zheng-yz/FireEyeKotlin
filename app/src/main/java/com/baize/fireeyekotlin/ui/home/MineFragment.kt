package com.baize.fireeyekotlin.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.ui.AdviseActivity
import com.baize.fireeyekotlin.utils.PerfectClickListener
import com.baize.fireeyekotlin.utils.showToast
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created by 彦泽 on 2018/9/27.
 */
class MineFragment : BaseFragment() {

    override fun setContent(): Int {
        return R.layout.fragment_mine
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        showContentView()
        val onclickEvent = OnclickEvent(context)
        tv_advise.setOnClickListener(onclickEvent)
        tv_save.setOnClickListener(onclickEvent)
        ll_header.setOnClickListener(onclickEvent)
        ll_collection.setOnClickListener(onclickEvent)
        ll_comment.setOnClickListener(onclickEvent)
    }

    class OnclickEvent(var context: Context?) : PerfectClickListener() {
        override fun onNoDoubleClick(v: View) {
            when (v.id) {
                R.id.tv_advise -> {
                    context?.startActivity(Intent(context, AdviseActivity::class.java))
                }
                R.id.tv_save -> {
                    context?.showToast(message = "我的缓存")
                }
                R.id.ll_header -> {
                    context?.showToast(message = "头像")
                }
                R.id.ll_collection -> {
                    context?.showToast(message = "收藏")
                }
                R.id.ll_comment -> {
                    context?.showToast(message = "评论")
                }
            }
        }
    }

}