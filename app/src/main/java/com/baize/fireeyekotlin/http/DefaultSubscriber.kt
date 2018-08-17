package com.baize.fireeyekotlin.http

import com.google.gson.JsonParseException

import org.json.JSONException

import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

import retrofit2.HttpException
import rx.Subscriber

/**
 * Created by 彦泽 on 2018/7/26.
 */

abstract class DefaultSubscriber<T> : Subscriber<T>() {
    override fun onCompleted() {}

    override fun onError(e: Throwable) {
        val errorMessage: String?
        if (e is HttpException) {     //   HTTP错误
            errorMessage = "HTTP错误"
        } else if (e is ConnectException || e is UnknownHostException) {   //   连接错误
            errorMessage = "网络错误"
        } else if (e is InterruptedIOException) {   //  连接超时
            errorMessage = "链接超时"
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {   //  解析错误
            errorMessage = "解析错误"
        } else {
            errorMessage = e.message
        }
        _onError(errorMessage.toString())
    }

    override fun onNext(t: T) {
        _onNext(t)
    }

    abstract fun _onError(errMsg: String)

    abstract fun _onNext(entity: T)
}
