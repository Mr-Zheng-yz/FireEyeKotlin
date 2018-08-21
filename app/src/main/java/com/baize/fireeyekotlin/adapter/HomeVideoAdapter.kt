package com.baize.fireeyekotlin.adapter

import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseRecyclerViewAdapter
import com.baize.fireeyekotlin.base.BaseRecyclerViewHolder
import com.baize.fireeyekotlin.databinding.ItemHomeVideoBinding
import com.baize.fireeyekotlin.mvvm.model.HomeBean

/**
 * Created by 彦泽 on 2018/8/21.
 */
class HomeVideoAdapter : BaseRecyclerViewAdapter<HomeBean.IssueListBean.ItemListBean>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<HomeBean.IssueListBean.ItemListBean, *> {
        return ViewHolder(parent, R.layout.item_home_video)
    }

    class ViewHolder(parent: ViewGroup, item_home_video: Int) : BaseRecyclerViewHolder<HomeBean.IssueListBean.ItemListBean, ItemHomeVideoBinding>(parent, item_home_video) {
        override fun onBindViewHolder(entity: HomeBean.IssueListBean.ItemListBean, position: Int) {
            binding.entity = entity
        }
    }
}