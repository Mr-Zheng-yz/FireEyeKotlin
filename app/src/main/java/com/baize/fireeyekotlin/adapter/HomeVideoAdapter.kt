package com.baize.fireeyekotlin.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseRecyclerViewAdapter
import com.baize.fireeyekotlin.base.BaseRecyclerViewHolder
import com.baize.fireeyekotlin.databinding.ItemHomeVideoBinding
import com.baize.fireeyekotlin.bean.HomeBean
import com.baize.fireeyekotlin.bean.VideoBean
import com.baize.fireeyekotlin.ui.VideoDetailActivity
import com.baize.fireeyekotlin.utils.DebugUtil
import com.baize.fireeyekotlin.utils.PerfectClickListener

/**
 * Created by 彦泽 on 2018/8/21.
 */
class HomeVideoAdapter(context: Context?) : BaseRecyclerViewAdapter<HomeBean.IssueListBean.ItemListBean>() {
    var context: Context? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<HomeBean.IssueListBean.ItemListBean, *> {
        return ViewHolder(parent, R.layout.item_home_video, context)
    }

    class ViewHolder(parent: ViewGroup, item_home_video: Int, context: Context?) : BaseRecyclerViewHolder<HomeBean.IssueListBean.ItemListBean, ItemHomeVideoBinding>(parent, item_home_video) {
        var context: Context? = null

        init {
            this.context = context
        }

        override fun onBindViewHolder(entity: HomeBean.IssueListBean.ItemListBean, position: Int) {
            binding.entity = entity
            binding.root.setOnClickListener(object : PerfectClickListener() {
                override fun onNoDoubleClick(v: View) {
                    var intent: Intent = Intent(context, VideoDetailActivity::class.java)
                    var photo = entity.data?.cover?.feed
                    var title = entity.data?.title
                    var desc = entity.data?.description
                    var duration = entity.data?.duration
                    var playUrl = entity.data?.playUrl
                    var category = entity.data?.category
                    var blurred = entity.data?.cover?.blurred
                    var collect = entity.data?.consumption?.collectionCount
                    var share = entity.data?.consumption?.shareCount
                    var reply = entity.data?.consumption?.replyCount
                    var time = System.currentTimeMillis()
                    var videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)

                    DebugUtil.e(msg = videoBean.toString())

                    intent.putExtra("data", videoBean as? Parcelable)
                    context?.let { context -> context.startActivity(intent) }
                }
            })
        }
    }
}