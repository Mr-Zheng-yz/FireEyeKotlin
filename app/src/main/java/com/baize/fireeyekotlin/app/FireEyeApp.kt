package com.baize.fireeyekotlin.app

import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * Created by 彦泽 on 2018/8/14.
 */

class FireEyeApp : MultiDexApplication() {

    companion object {
        var instance: Context? = null
        fun instance(): Context = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
