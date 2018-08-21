package com.baize.fireeyekotlin.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.baize.fireeyekotlin.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * DataBinding加载图片
 * Created by 彦泽 on 2018/8/20.
 */
class ImgLoadUtil {
    companion object {

        //加载首页图片
        @BindingAdapter("load_img")
        @JvmStatic
        fun loadImg(iv: ImageView, imgUrl: String?) {
            if (imgUrl == null) {
                return
            }
            Glide.with(iv.context)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_two_bi_one)
                    .error(R.drawable.load_err)
                    .crossFade(3000)
                    .into(iv)
        }

        //加载首页电影作者头像
        @BindingAdapter("load_high_img")
        @JvmStatic
        fun loadHighImg(iv: ImageView, imgUrl: String?) {
            if (imgUrl == null) {
                return
            }
            Glide.with(iv.context)
                    .load(imgUrl)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.img_default_movie)
                    .error(R.drawable.load_err)
                    .into(iv)
        }
    }
}