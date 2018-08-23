package com.baize.fireeyekotlin.ui

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.bean.VideoBean
import com.baize.fireeyekotlin.databinding.ActivityVideoDetailBinding
import com.baize.fireeyekotlin.utils.DebugUtil
import com.baize.fireeyekotlin.utils.showToast
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.FileInputStream

//TODO 视频暂停后旋转，Video控件变黑屏问题。 视频下载功能。  分享功能。

class VideoDetailActivity : AppCompatActivity() {
    lateinit var bindingView: ActivityVideoDetailBinding
    lateinit var orientation: OrientationUtils
    lateinit var imageView: ImageView
    lateinit var bean: VideoBean
    var isPlay: Boolean = false
    var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = DataBindingUtil.setContentView<ActivityVideoDetailBinding>(this, R.layout.activity_video_detail)
        bean = intent.getParcelableExtra<VideoBean>("data")
        initView()
        prepareVideo()
    }

    //初始化布局
    private fun initView() {
        bindingView.entity = bean
    }

    //准备视频播放
    private fun prepareVideo() {
        //区分本地视频与网络视频
        val uri = intent.getStringExtra("local")
        if (uri != null) {
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(bean.playUrl, false, null, null)
        }

        //外部辅助的旋转，帮助全屏
        orientation = OrientationUtils(this, gsy_player)
        //初始化不打开外部的旋转
        orientation.isEnable = false

        //增加封面
        imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageViewAsync(bean.feed, imageView)

        gsy_player.titleTextView.visibility = View.VISIBLE
        gsy_player.backButton.visibility
        gsy_player.backButton.setOnClickListener({ onBackPressed() })
        //是否可以滑动调整等
        gsy_player.setIsTouchWiget(true)
        gsy_player.isRotateViewAuto = false
        gsy_player.isLockLand = false
        gsy_player.isAutoFullWithSize = true
        gsy_player.isShowFullAnimation = false
        gsy_player.isNeedLockFull = true
        gsy_player.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientation.setEnable(true)
                isPlay = true
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                DebugUtil.e(msg = "***** onQuitFullscreen **** $objects[0]")
                DebugUtil.e(msg = "***** onQuitFullscreen **** objects[1]")//当前非全屏player
                orientation.backToProtVideo()
            }
        })

        //点击全屏播放按钮
        gsy_player.fullscreenButton.setOnClickListener {
            showToast("横屏或竖屏")
            //直接横屏
            orientation.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(this, true, true)
        }
    }

    //异步下载图片并显示
    fun ImageViewAsync(path: String?, imageView: ImageView) {
        rx.Observable.unsafeCreate(Observable.OnSubscribe<String> { t ->
            val future = Glide.with(this@VideoDetailActivity)
                    .load(path)
                    .downloadOnly(100, 100)
            val cacheFile = future.get()
            val mPath = cacheFile.absolutePath
            t.onNext(mPath)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    val mIs = FileInputStream(t)
                    val bitmap = BitmapFactory.decodeStream(mIs)
                    imageView.setImageBitmap(bitmap)
                    gsy_player.thumbImageView = imageView
                }
    }

    override fun onPause() {
        gsy_player.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        gsy_player.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientation.let { orientation -> orientation.releaseListener() }
    }

    override fun onBackPressed() {
        orientation.let { orientation.backToProtVideo() }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            gsy_player.onConfigurationChanged(this, newConfig, orientation, true, true);
//            if (isPlay && !isPause) {
//                if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
//                    if (!gsy_player.isIfCurrentIsFullscreen) {
//                        gsy_player.startWindowFullscreen(this, true, true)
//                    }
//                } else {
//                    //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
//                    if (gsy_player.isIfCurrentIsFullscreen) {
//                        GSYVideoManager.backFromWindowFull(this)
//                    }
//                    orientation?.let { orientation.isEnable = true }
//                }
//            }
        }
    }
}
