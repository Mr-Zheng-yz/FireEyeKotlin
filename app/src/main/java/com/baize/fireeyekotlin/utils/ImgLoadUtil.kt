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

        //加载首页视频作者头像：
        /**
         * 这里placeholder()导致了图片变形
         * 因为Glide默认开启的crossFade动画导致TransitionDrawable绘制异常
         * 根本原因就是placeholder图片和要加载显示图片的宽高不一致，导致了TransitionDrawable无法很好地处理不同宽高比的过度问题
         * 解决：
         *  1.dontAnimation()
         *  2.取消placeholder()
         *  3.placeholder()图片宽高小于要加载的图片
         *  4.animate（）方法自己写动画，自己修复TransitionDrawable问题
         *      http://www.cnblogs.com/ldq2016/p/7249892.html
         *
         * 这里由于首页视频大图和作者头像占位图不一样，并且需要crossFade动画，所以只好写两个，日后想办法优化
         */
        @BindingAdapter("load_noplace_img")
        @JvmStatic
        fun loadNoplaceImg(iv: ImageView, imgUrl: String?) {
            if (imgUrl == null) {
                return
            }
            Glide.with(iv.context)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.load_err)
                    .crossFade(3000)
                    .into(iv)
        }

        /**
         * 显示高斯模糊图片
         */
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
                    .placeholder(R.drawable.stackblur_default)
                    .error(R.drawable.stackblur_default)
                    .into(iv)
        }
    }
}