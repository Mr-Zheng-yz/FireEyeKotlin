package com.baize.fireeyekotlin.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.utils.PerfectClickListener
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.layout_empty_view.*
import kotlinx.android.synthetic.main.layout_loading_view.*
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by 彦泽 on 2018/9/6.
 */
abstract class BaseFragment : Fragment() {

    // 子布局内容view
    private lateinit var mContentView: View
    // 加载中
    private lateinit var loadingView: View
    // 加载失败，刷新布局
    private lateinit var errorView: View
    //空布局
    private lateinit var emptyView: View
    // fragment是否显示了
    protected var mIsVisible = false
    // 动画
    private var mAnimationDrawable: AnimationDrawable? = null

    private var mCompositeSubscription: CompositeSubscription? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ll = inflater.inflate(R.layout.fragment_base, null)
        mContentView = activity!!.layoutInflater.inflate(setContent(), null, false)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mContentView.layoutParams = params
        val container: RelativeLayout = ll.findViewById(R.id.container)
        container.addView(mContentView)
        return ll
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            mIsVisible = true
            onVisible()
        } else {
            mIsVisible = false
            onInvisible()
        }
    }

    protected open fun onInvisible() {}

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected open fun loadData() {}

    protected fun onVisible() {
        loadData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadingView = vs_loading.inflate()
        errorView = vs_error.inflate()
        emptyView = vs_empty.inflate()

        // 加载动画
        mAnimationDrawable = img_progress.drawable as AnimationDrawable?
        // 默认进入页面就开启动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        // 点击加载失败布局
        errorView.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                showLoading()
                onRefresh()
            }
        })

        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
        mContentView.visibility = View.GONE
    }

    /**
     * 布局
     */
    abstract fun setContent(): Int

    /**
     * 加载失败后点击后的操作
     */
    protected open fun onRefresh() {
    }

    /**
     * 显示加载中状态
     */
    protected fun showLoading() {
        if (loadingView.visibility != View.VISIBLE) {
            loadingView.visibility = View.VISIBLE
        }
        // 开始动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        if (mContentView.visibility != View.GONE) {
            mContentView.visibility = View.GONE
        }
        if (errorView.visibility != View.GONE) {
            errorView.visibility = View.GONE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    /**
     * 加载完成的状态
     */
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
        if (mContentView.visibility != View.VISIBLE) {
            mContentView.visibility = View.VISIBLE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
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
        if (mContentView.visibility != View.GONE) {
            mContentView.visibility = View.GONE
        }
        if (emptyView.visibility != View.GONE) {
            emptyView.visibility = View.GONE
        }
    }

    /**
     * 显示空布局
     */
    protected fun showEmpty(tips: String = "数据为空哦~") {
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
        if (mContentView.visibility != View.GONE) {
            mContentView.visibility = View.GONE
        }
        if (emptyView.visibility != View.VISIBLE) {
            tv_empty_tips.text = tips
            emptyView.visibility = View.VISIBLE
        }
    }

    fun addSubscription(s: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }
        this.mCompositeSubscription!!.add(s)
    }

    override fun onDestroy() {
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