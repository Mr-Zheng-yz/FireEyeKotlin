package com.baize.fireeyekotlin.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.utils.CommonUtils
import com.baize.fireeyekotlin.utils.PerfectClickListener
import com.baize.fireeyekotlin.utils.statubar.StatusBarUtil

import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_empty_view.*
import kotlinx.android.synthetic.main.layout_loading_view.*

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by 彦泽 on 2018/7/27.
 */

open class BaseActivity : AppCompatActivity() {
    //布局View
    private lateinit var baseContentView: View

    private lateinit var childContentView: View
    private lateinit var errorView: View
    private lateinit var loadingView: View
    private lateinit var emptyView: View
    private var mAnimationDrawable: AnimationDrawable? = null
    //订阅容器
    private var mCompositeSubscription: CompositeSubscription? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        baseContentView = LayoutInflater.from(this).inflate(R.layout.activity_base, null, false)
        childContentView = layoutInflater.inflate(layoutResID, null, false)

        // content
        val container = baseContentView.findViewById<RelativeLayout>(R.id.container)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        childContentView.layoutParams = params
        container.addView(childContentView)
        window.setContentView(baseContentView)

        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.white), 0)
        loadingView = vs_loading.inflate()
        errorView = vs_error.inflate()
        emptyView = vs_empty.inflate()

        // 加载动画
        mAnimationDrawable = img_progress.drawable as? AnimationDrawable?
        // 默认进入页面就开启动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }

        setToolBar()
        // 点击加载失败布局
        errorView.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                showLoading()
                onRefresh()
            }
        })
        childContentView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    override fun setTitle(text: CharSequence) {
        tv_toolbar_title.text = text
    }

    /**
     * 设置titlebar
     */
    protected fun setToolBar() {
        setSupportActionBar(tool_bar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_24dp)
        }
        tool_bar.setNavigationOnClickListener({ onBackPressed() })
    }

    //显示加载中布局
    protected fun showLoading() {
        if (loadingView != null && loadingView!!.visibility != View.VISIBLE) {
            loadingView!!.visibility = View.VISIBLE
        }
        // 开始动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        if (childContentView.visibility != View.GONE) {
            childContentView.visibility = View.GONE
        }
        if (errorView.visibility != View.GONE) {
            errorView.visibility = View.GONE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    //显示内容布局
    protected fun showContentView() {
        if (loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView.visibility != View.GONE) {
            errorView.visibility = View.GONE
        }
        if (childContentView.visibility != View.VISIBLE) {
            childContentView.visibility = View.VISIBLE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    //显示错误布局
    protected fun showError() {
        if (loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView.visibility != View.VISIBLE) {
            errorView.visibility = View.VISIBLE
        }
        if (childContentView.visibility != View.GONE) {
            childContentView.visibility = View.GONE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    //显示空布局
    protected fun showEmpty(tips: String = "数据暂时为空哦~") {
        if (loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView.visibility != View.GONE) {
            errorView.visibility = View.GONE
        }
        if (childContentView.visibility != View.GONE) {
            childContentView.visibility = View.GONE
        }
        if (emptyView.visibility != View.VISIBLE) {
            tv_empty_tips.text = tips
            emptyView.visibility = View.VISIBLE
        }
    }

    /**
     * 失败后点击刷新
     */
    open protected fun onRefresh() {}

    fun addSubscription(s: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }
        this.mCompositeSubscription!!.add(s)
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (this.mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
            this.mCompositeSubscription!!.unsubscribe()
        }
    }

    fun removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
            this.mCompositeSubscription!!.unsubscribe()
        }
    }
}
