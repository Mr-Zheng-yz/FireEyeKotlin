package com.baize.fireeyekotlin.utils

import android.os.Debug
import android.os.Looper
import android.util.Log

/**
 * Created by 彦泽 on 2018/8/15.
 */
class DebugUtil {
    companion object {
        val TAG = "baize"
        val DEBUG = true

        fun d(tag: String = TAG, msg: String) {
            if (DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun e(tag: String = TAG, msg: String) {
            if (DEBUG) {
                Log.e(tag, msg)
            }
        }

        fun isMainThread() {
            if (DEBUG) {
                Log.e(TAG, "---是否在主线程： ${Thread.currentThread() == Looper.getMainLooper().thread}")
            }
        }
    }

}