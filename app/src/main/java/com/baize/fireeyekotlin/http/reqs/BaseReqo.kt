package com.baize.fireeyekotlin.http.reqs


import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by 彦泽 on 2018/8/15.
 */

open class BaseReqo {
    protected fun transform(observable: Observable<*>): Observable<*> {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
