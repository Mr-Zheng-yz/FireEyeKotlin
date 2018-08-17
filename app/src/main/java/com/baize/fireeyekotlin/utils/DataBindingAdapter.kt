package com.baize.fireeyekotlin.utils

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.baize.fireeyekotlin.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_base.*

/**
 * Created by 彦泽 on 2018/8/16.
 */

class DataBindingAdapter{
    companion object {

        @BindingAdapter("img_url")
        @JvmStatic
        fun imgUrl(iv:ImageView,imgUrl:String){
            Glide.with(iv.context)
                    .load(imgUrl)
                    .into(iv)
        }

        /**
         * 显示高斯模糊效果
         */
//        @JvmStatic
//        fun displayGaussian(context: Context,url:String,iv:ImageView ){
//            Glide.with(context)
//                    .load(url)
//                    .error(R.drawable.stackblur_default)
//                    .placeholder(R.drawable.stackblur_default)
//                    .crossFade(500)
//                    .into(iv)
//        }

    }


}
