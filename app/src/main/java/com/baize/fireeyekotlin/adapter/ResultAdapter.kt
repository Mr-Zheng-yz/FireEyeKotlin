package com.baize.fireeyekotlin.adapter

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseRecyclerViewAdapter
import com.baize.fireeyekotlin.base.BaseRecyclerViewHolder
import com.baize.fireeyekotlin.bean.HotBean
import com.baize.fireeyekotlin.bean.VideoBean
import com.baize.fireeyekotlin.databinding.ItemResultBinding
import com.baize.fireeyekotlin.ui.VideoDetailActivity
import com.baize.fireeyekotlin.utils.PerfectClickListener
import com.baize.fireeyekotlin.utils.showToast
import java.text.SimpleDateFormat

/**
 * Created by 彦泽 on 2018/9/28.
 */
class ResultAdapter(var mContext: Context?) : BaseRecyclerViewAdapter<HotBean.ItemListBean.DataBean>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<HotBean.ItemListBean.DataBean, *> {
        return ViewHolder(parent, R.layout.item_result, mContext)
    }

    class ViewHolder(parent: ViewGroup, viewType: Int, var mContext: Context?) : BaseRecyclerViewHolder<HotBean.ItemListBean.DataBean, ItemResultBinding>(parent, viewType) {
        override fun onBindViewHolder(entity: HotBean.ItemListBean.DataBean, position: Int) {
            binding.entity = entity

            //分钟和秒
            val time_end = if ((entity.duration % 60) < 10) {
                "0${entity.duration % 60}"
            } else {
                "${entity.duration % 60}"
            }

            //日期
            val smf: SimpleDateFormat = SimpleDateFormat("MM-dd")
            val date = smf.format(entity.releaseTime)

            //类型 + 时长 + 日期
            val detail = "${entity.category} / ${entity.duration / 60}'$time_end / $date"
            binding.tvDetail.text = detail

            //点击跳转视频详情
            binding.root.setOnClickListener(object : PerfectClickListener() {
                override fun onNoDoubleClick(v: View) {
                    mContext?.showToast(entity.title!!)
                    //=====
                    //跳转视频详情页
                    var intent = Intent(mContext, VideoDetailActivity::class.java)
                    var desc = entity.description
                    var playUrl = entity.playUrl
                    var blurred = entity.cover?.blurred
                    var collect = entity.consumption?.collectionCount
                    var share = entity.consumption?.shareCount
                    var reply = entity.consumption?.replyCount
                    var time = System.currentTimeMillis()
                    var videoBean = VideoBean(entity.cover?.feed, entity.title, desc, entity.duration, playUrl, entity.category, blurred, collect, share, reply, time)
                    intent.putExtra("data", videoBean as Parcelable)
                    mContext?.let { context -> context.startActivity(intent) }
                }
            })
        }
    }
}