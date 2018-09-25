package com.baize.fireeyekotlin.test

import android.os.Bundle
import com.baize.fireeyekotlin.R
import com.baize.fireeyekotlin.base.BaseFragment
import com.baize.fireeyekotlin.utils.log.L
import kotlinx.android.synthetic.main.fragment_test.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by 彦泽 on 2018/8/14.
 */
class TestFragment : BaseFragment() {
    override fun setContent(): Int {
        return R.layout.fragment_test
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_show_content.setOnClickListener({
            showContentView()
        })

        btn_show_empty.setOnClickListener({
            showEmpty("天啦噜")
            L.e(msg = "showEmpty")
            Observable.unsafeCreate(Observable.OnSubscribe<String> { t ->
                Thread.sleep(2000)
                t.onNext("睡醒")
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        showContentView()
                        L.e(msg = "showContentView")
                    })
        })

        btn_show_error.setOnClickListener({
            showError()
        })

        btn_show_loading.setOnClickListener({
            showLoading()
            Observable.unsafeCreate(Observable.OnSubscribe<String> { t ->
                Thread.sleep(2000)
                t.onNext("睡醒")
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ t ->
                        showContentView()
                        L.e(msg = "showContentView")
                    })
        })

        showContentView()
    }

    override fun onRefresh() {
        showContentView()
    }
}