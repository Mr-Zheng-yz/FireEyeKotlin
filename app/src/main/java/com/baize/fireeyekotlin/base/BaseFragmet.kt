package com.baize.fireeyekotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.utils.PerfectClickListener
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.layout_loading_view.*

/**
 * Created by 彦泽 on 2018/8/15.
 */
abstract class BaseFragment<SV : ViewDataBinding> : Fragment() {

    // 布局view
    protected lateinit var bindingView: SV
    // fragment是否显示了
    protected var mIsVisible = false
    // 加载中
    private var loadingView: View? = null
    // 加载失败，刷新布局
    private var mRefresh: LinearLayout? = null
    // 内容布局
    protected lateinit var mContainer: RelativeLayout
    // 动画
    private var mAnimationDrawable: AnimationDrawable? = null

//    private var mCompositeSubscription: CompositeSubscription? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ll = inflater.inflate(R.layout.fragment_base, null)
        bindingView = DataBindingUtil.inflate(activity!!.layoutInflater, setContent(), null, false)
        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        bindingView.root.layoutParams = params
        mContainer = ll.findViewById(R.id.container)
        mContainer.addView(bindingView.root)
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

        // 加载动画
        mAnimationDrawable = img_progress.getDrawable() as AnimationDrawable?
        // 默认进入页面就开启动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        mRefresh = getView(R.id.ll_error_refresh)
        // 点击加载失败布局
        mRefresh!!.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                showLoading()
                onRefresh()
            }
        })
        bindingView.root.visibility = View.GONE

    }

    protected fun <T : View> getView(id: Int): T {
        return view!!.findViewById<View>(id) as T
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
        if (loadingView != null && loadingView!!.visibility != View.VISIBLE) {
            loadingView!!.visibility = View.VISIBLE
        }
        // 开始动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        if (bindingView.root.visibility != View.GONE) {
            bindingView.root.visibility = View.GONE
        }
        if (mRefresh!!.visibility != View.GONE) {
            mRefresh!!.visibility = View.GONE
        }
    }

    /**
     * 加载完成的状态
     */
    protected fun showContentView() {
        if (loadingView != null && loadingView!!.visibility != View.GONE) {
            loadingView!!.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (mRefresh!!.visibility != View.GONE) {
            mRefresh!!.visibility = View.GONE
        }
        if (bindingView.root.visibility != View.VISIBLE) {
            bindingView.root.visibility = View.VISIBLE
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected fun showError() {
        if (loadingView != null && loadingView!!.visibility != View.GONE) {
            loadingView!!.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (mRefresh!!.visibility != View.VISIBLE) {
            mRefresh!!.visibility = View.VISIBLE
        }
        if (bindingView.root.visibility != View.GONE) {
            bindingView.root.visibility = View.GONE
        }
    }

//    fun addSubscription(s: Subscription) {
//        if (this.mCompositeSubscription == null) {
//            this.mCompositeSubscription = CompositeSubscription()
//        }
//        this.mCompositeSubscription!!.add(s)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (this.mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
//            this.mCompositeSubscription!!.unsubscribe()
//        }
//    }
//
//    fun removeSubscription() {
//        if (this.mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
//            this.mCompositeSubscription!!.unsubscribe()
//        }
//    }
}
