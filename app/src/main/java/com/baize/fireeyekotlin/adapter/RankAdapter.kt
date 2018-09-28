package com.baize.fireeyekotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseRecyclerViewAdapter
import com.baize.fireeyekotlin.base.BaseRecyclerViewHolder
import com.baize.fireeyekotlin.bean.FindBean
import com.baize.fireeyekotlin.databinding.ItemRankBinding
import com.baize.fireeyekotlin.ui.RankDetailActivity
import com.baize.fireeyekotlin.utils.showToast

/**
 * Created by 彦泽 on 2018/9/25.
 */
class RankAdapter(context: Context?) : BaseRecyclerViewAdapter<FindBean>() {

    var mContext: Context?

    init {
        this.mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<FindBean, *> {
        return ViewHolder(parent, R.layout.item_rank, mContext)
    }

    class ViewHolder(parent: ViewGroup, item_rank: Int, context: Context?)
        : BaseRecyclerViewHolder<FindBean, ItemRankBinding>(parent, item_rank) {

        var mContext: Context? = null

        init {
            this.mContext = context
        }

        override fun onBindViewHolder(entity: FindBean, position: Int) {
            binding.entity = entity
            binding.tvTitle.text = entity.name
            binding.root.setOnClickListener {
                mContext?.showToast(message = entity.name)
                val intent = Intent(mContext, RankDetailActivity::class.java)
                intent.putExtra("name", entity.name)
                mContext?.startActivity(intent)
            }
        }

    }

}