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
import com.baize.fireeyekotlin.databinding.ItemRankBinding
import com.baize.fireeyekotlin.ui.VideoDetailActivity
import com.baize.fireeyekotlin.utils.ImgLoadUtil
import com.baize.fireeyekotlin.utils.PerfectClickListener
import com.baize.fireeyekotlin.utils.showToast

/**
 * Created by 彦泽 on 2018/9/27.
 */
class FindAdapter(context: Context?) : BaseRecyclerViewAdapter<HotBean.ItemListBean.DataBean>() {
    private var mContext: Context? = null

    init {
        this.mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<HotBean.ItemListBean.DataBean, *> {
        return ViewHolder(parent, R.layout.item_rank, mContext)
    }

    class ViewHolder(viewGroup: ViewGroup, layoutId: Int, context: Context?) : BaseRecyclerViewHolder<HotBean.ItemListBean.DataBean
            , ItemRankBinding>(viewGroup, layoutId) {
        private var mContext: Context? = null

        init {
            this.mContext = context
        }

        override fun onBindViewHolder(entity: HotBean.ItemListBean.DataBean, position: Int) {
            ImgLoadUtil.loadImg(binding.ivPhoto, entity.cover?.feed)
            val time_end = if ((entity.duration % 60) < 10) {
                "0${entity.duration % 60}"
            } else {
                "${entity.duration % 60}"
            }
            val time = "${entity.category} / ${entity.duration / 60}'$time_end"
            binding.tvTime.text = time
            binding.tvTitle.text = entity.title

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
                    var videoBean  = VideoBean(entity.cover?.feed,entity.title,desc,entity.duration,playUrl,entity.category,blurred,collect ,share ,reply,time)
//                    var url = SPUtils.getInstance(context!!,"beans").getString(playUrl!!)
//                    if(url.equals("")){
//                        var count = SPUtils.getInstance(context!!,"beans").getInt("count")
//                        if(count!=-1){
//                            count = count.inc()
//                        }else{
//                            count = 1
//                        }
//                        SPUtils.getInstance(context!!,"beans").put("count",count)
//                        SPUtils.getInstance(context!!,"beans").put(playUrl!!,playUrl)
//                        ObjectSaveUtils.saveObject(context!!,"bean$count",videoBean)
//                    }
                    intent.putExtra("data",videoBean as Parcelable)
                    mContext?.let { context -> context.startActivity(intent) }
                    //====
                }
            })
        }
    }
}